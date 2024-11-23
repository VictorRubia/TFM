package com.victorrubia.tfg.data.repository.ppg_measure

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of the [PPGMeasureRepository] interface that works with data sources.
 *
 * @property ppgMeasureCacheDataSource the cache data source that is used to store and retrieve data.
 * @property ppgMeasureLocalDataSource the local data source that is used to store and retrieve data.
 * @property ppgMeasureRemoteDataSource the remote data source that is used to store and retrieve data.
 */
class PPGMeasureRepositoryImpl(
    private val ppgMeasureRemoteDataSource: PPGMeasureRemoteDataSource,
    private val ppgMeasureLocalDataSource: PPGMeasureLocalDataSource,
    private val ppgMeasureCacheDataSource: PPGMeasureCacheDataSource,
) : PPGMeasureRepository {

    /**
     * Variable that stores number of times a measurement has taken place.
     */
    private var count = 0
    /**
     * The [MutableState] that stores the status of internet connection.
     */
    private val internetStatus = mutableStateOf(true)


    private val mutex = Mutex()

    override suspend fun savePPGMeasure(ppgMeasure: PPGMeasure, activityId: Int): MutableState<Boolean> {
        mutex.withLock {
            try {
                ppgMeasureCacheDataSource.addPPGMeasureToCache(ppgMeasure)
                count++
                if (count >= 1000) {
                    val cachedMeasures = ppgMeasureCacheDataSource.getPPGMeasureFromCache()
                    sendPPGMeasureToAPI(cachedMeasures, activityId)
                    count = 0
                }
            } catch (exception: Exception) {
                Log.e("MyTag", exception.message.toString())
            }
            Unit // Agregar esta línea
        }
        return internetStatus
    }


    override suspend fun endPPGMeasure(activityId : Int) {
        try {
            sendPPGMeasureToAPI(ppgMeasureCacheDataSource.getPPGMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Sends a list of [PPGMeasure] to the API.
     *
     * @param ppgMeasure the list of [PPGMeasure] to be sent.
     * @param activityId the id of the activity that the [PPGMeasure] belongs to.
     */
    suspend fun sendPPGMeasureToAPI(ppgMeasure: List<PPGMeasure>, activityId: Int) {
        try {
            checkExistingMeasurements(activityId)
            val jsonData = Json.encodeToString(ppgMeasure)
            val response = ppgMeasureRemoteDataSource.sendPPGMeasures(jsonData, activityId)
            if (response.isSuccessful) {
                ppgMeasureLocalDataSource.clearAll()
                ppgMeasureCacheDataSource.clearAll()
                internetStatus.value = true
            } else {
                savePPGMeasuresToDB(ppgMeasure)
                Log.e("PPGData", "Failed to send data. Response code: ${response.code()}")
            }
        } catch (exception: Exception) {
            internetStatus.value = false
            Log.e("PPGData", "Exception while sending data: ${exception.message}")
            savePPGMeasuresToDB(ppgMeasure)
        }
    }

    /**
     * Saves a list of [PPGMeasure] to the local database.
     *
     * @param ppgMeasures the list of [PPGMeasure] to be saved.
     */
    suspend fun savePPGMeasuresToDB(ppgMeasures: List<PPGMeasure>){
        try {
            ppgMeasures.forEach{ppgMeasure -> ppgMeasureLocalDataSource.addPPGMeasureToDB(ppgMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Checks if there are any [PPGMeasure] stored in the local database which
     * were not sent to the API in order to send them to the API.
     *
     * @param activityId the id of the activity that the [PPGMeasure] belongs to.
     */
    suspend fun checkExistingMeasurements(activityId: Int) {
        try {
            val list = ppgMeasureLocalDataSource.getPPGMeasureFromDB()
            if (list.isNotEmpty()) {
                val response = ppgMeasureRemoteDataSource.sendPPGMeasures(Json.encodeToString(list), activityId)
                if (response.isSuccessful) {
                    ppgMeasureLocalDataSource.clearAll()
                } else {
                    Log.e("MyTag", "Failed to send cached measures, response code: ${response.code()}")
                }
            }
        } catch (exception: Exception) {
            Log.e("MyTag", exception.message.toString())
        }
    }


}