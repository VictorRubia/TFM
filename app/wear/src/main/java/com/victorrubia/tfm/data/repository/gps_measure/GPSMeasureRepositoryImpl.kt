package com.victorrubia.tfm.data.repository.gps_measure

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfm.domain.repository.GPSMeasureRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of the [GPSMeasureRepository] interface that works with data sources.
 *
 * @property gpsMeasureCacheDataSource the cache data source that is used to store and retrieve data.
 * @property gpsMeasureLocalDataSource the local data source that is used to store and retrieve data.
 * @property gpsMeasureRemoteDataSource the remote data source that is used to store and retrieve data.
 */
class GPSMeasureRepositoryImpl(
    private val gpsMeasureRemoteDataSource: GPSMeasureRemoteDataSource,
    private val gpsMeasureLocalDataSource: GPSMeasureLocalDataSource,
    private val gpsMeasureCacheDataSource: GPSMeasureCacheDataSource,
) : GPSMeasureRepository {

    /**
     * Variable that stores number of times a measurement has taken place.
     */
    private var count = 0
    /**
     * The [MutableState] that stores the status of internet connection.
     */
    private val internetStatus = mutableStateOf(true)


    override suspend fun saveGPSMeasure(gpsMeasure: GPSMeasure, activityId: Int): MutableState<Boolean> {

        try {
            gpsMeasureCacheDataSource.addGPSMeasureToCache(gpsMeasure)
            count++
            if(count == 5){
                count = 0
                sendGPSMeasureToAPI(gpsMeasureCacheDataSource.getGPSMeasureFromCache(), activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return internetStatus
    }


    override suspend fun endGPSMeasure(activityId : Int) {
        try {
            sendGPSMeasureToAPI(gpsMeasureCacheDataSource.getGPSMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Sends a list of [GPSMeasure] to the API.
     *
     * @param gpsMeasure the list of [GPSMeasure] to be sent.
     * @param activityId the id of the activity that the [GPSMeasure] belongs to.
     */
    suspend fun sendGPSMeasureToAPI(gpsMeasure : List<GPSMeasure>, activityId : Int){
        try{
//            checkExistingMeasurements(activityId)
            val response = gpsMeasureRemoteDataSource.sendGPSMeasures(Json.encodeToString(gpsMeasure), activityId)
            val code = response.code()
            if(code == 201){
                gpsMeasureLocalDataSource.clearAll()
                gpsMeasureCacheDataSource.clearAll()
                internetStatus.value = true
            }
            else{
                saveGPSMeasuresToDB(gpsMeasure)
            }
        }
        catch (exception : Exception){
            internetStatus.value = false
            Log.e("MyTag", "SendGPSMeasureToAPI - " + exception.message.toString())
        }
    }

    /**
     * Saves a list of [GPSMeasure] to the local database.
     *
     * @param gpsMeasures the list of [GPSMeasure] to be saved.
     */
    suspend fun saveGPSMeasuresToDB(gpsMeasures: List<GPSMeasure>){
        try {
            gpsMeasures.forEach{gpsMeasure -> gpsMeasureLocalDataSource.addGPSMeasureToDB(gpsMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Checks if there are any [GPSMeasure] stored in the local database which
     * were not sent to the API in order to send them to the API.
     *
     * @param activityId the id of the activity that the [GPSMeasure] belongs to.
     */
    suspend fun checkExistingMeasurements(activityId: Int){
        try {
            val list = gpsMeasureLocalDataSource.getGPSMeasureFromDB()
            if(list.isNotEmpty()){
                sendGPSMeasureToAPI(list, activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

}