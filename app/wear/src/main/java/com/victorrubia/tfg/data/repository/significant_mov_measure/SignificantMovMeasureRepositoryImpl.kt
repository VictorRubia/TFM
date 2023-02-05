package com.victorrubia.tfg.data.repository.significantMov_measure

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfg.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfg.domain.repository.SignificantMovMeasureRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of the [SignificantMovMeasureRepository] interface that works with data sources.
 *
 * @property significantMovMeasureCacheDataSource the cache data source that is used to store and retrieve data.
 * @property significantMovMeasureLocalDataSource the local data source that is used to store and retrieve data.
 * @property significantMovMeasureRemoteDataSource the remote data source that is used to store and retrieve data.
 */
class SignificantMovMeasureRepositoryImpl(
    private val significantMovMeasureRemoteDataSource: SignificantMovMeasureRemoteDataSource,
    private val significantMovMeasureLocalDataSource: SignificantMovMeasureLocalDataSource,
    private val significantMovMeasureCacheDataSource: SignificantMovMeasureCacheDataSource,
) : SignificantMovMeasureRepository {

    /**
     * Variable that stores number of times a measurement has taken place.
     */
    private var count = 0
    /**
     * The [MutableState] that stores the status of internet connection.
     */
    private val internetStatus = mutableStateOf(true)


    override suspend fun saveSignificantMovMeasure(significantMovMeasure: SignificantMovMeasure, activityId: Int): MutableState<Boolean> {

        try {
            significantMovMeasureCacheDataSource.addSignificantMovMeasureToCache(significantMovMeasure)
            sendSignificantMovMeasureToAPI(significantMovMeasureCacheDataSource.getSignificantMovMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return internetStatus
    }


    override suspend fun endSignificantMovMeasure(activityId : Int) {
        try {
            sendSignificantMovMeasureToAPI(significantMovMeasureCacheDataSource.getSignificantMovMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Sends a list of [SignificantMovMeasure] to the API.
     *
     * @param significantMovMeasure the list of [SignificantMovMeasure] to be sent.
     * @param activityId the id of the activity that the [SignificantMovMeasure] belongs to.
     */
    suspend fun sendSignificantMovMeasureToAPI(significantMovMeasure : List<SignificantMovMeasure>, activityId : Int){
        try{
//            checkExistingMeasurements(activityId)
            val response = significantMovMeasureRemoteDataSource.sendSignificantMovMeasures(Json.encodeToString(significantMovMeasure), activityId)
            val code = response.code()
            if(code == 201){
                significantMovMeasureLocalDataSource.clearAll()
                significantMovMeasureCacheDataSource.clearAll()
                internetStatus.value = true
            }
            else{
                saveSignificantMovMeasuresToDB(significantMovMeasure)
            }
        }
        catch (exception : Exception){
            internetStatus.value = false
            Log.e("MyTag", "SendSignificantMovMeasureToAPI - " + exception.message.toString())
        }
    }

    /**
     * Saves a list of [SignificantMovMeasure] to the local database.
     *
     * @param significantMovMeasures the list of [SignificantMovMeasure] to be saved.
     */
    suspend fun saveSignificantMovMeasuresToDB(significantMovMeasures: List<SignificantMovMeasure>){
        try {
            significantMovMeasures.forEach{significantMovMeasure -> significantMovMeasureLocalDataSource.addSignificantMovMeasureToDB(significantMovMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Checks if there are any [SignificantMovMeasure] stored in the local database which
     * were not sent to the API in order to send them to the API.
     *
     * @param activityId the id of the activity that the [SignificantMovMeasure] belongs to.
     */
    suspend fun checkExistingMeasurements(activityId: Int){
        try {
            val list = significantMovMeasureLocalDataSource.getSignificantMovMeasureFromDB()
            if(list.isNotEmpty()){
                sendSignificantMovMeasureToAPI(list, activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

}