package com.victorrubia.tfg.data.repository.step_measure

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfg.data.model.step_measure.StepMeasure
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfg.domain.repository.StepMeasureRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of the [StepMeasureRepository] interface that works with data sources.
 *
 * @property stepMeasureCacheDataSource the cache data source that is used to store and retrieve data.
 * @property stepMeasureLocalDataSource the local data source that is used to store and retrieve data.
 * @property stepMeasureRemoteDataSource the remote data source that is used to store and retrieve data.
 */
class StepMeasureRepositoryImpl(
    private val stepMeasureRemoteDataSource: StepMeasureRemoteDataSource,
    private val stepMeasureLocalDataSource: StepMeasureLocalDataSource,
    private val stepMeasureCacheDataSource: StepMeasureCacheDataSource,
) : StepMeasureRepository {

    /**
     * Variable that stores number of times a measurement has taken place.
     */
    private var count = 0
    /**
     * The [MutableState] that stores the status of internet connection.
     */
    private val internetStatus = mutableStateOf(true)


    override suspend fun saveStepMeasure(stepMeasure: StepMeasure, activityId: Int): MutableState<Boolean> {

        try {
            stepMeasureCacheDataSource.addStepMeasureToCache(stepMeasure)
            count++
            if(count == 5){
                count = 0
                sendStepMeasureToAPI(stepMeasureCacheDataSource.getStepMeasureFromCache(), activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return internetStatus
    }


    override suspend fun endStepMeasure(activityId : Int) {
        try {
            sendStepMeasureToAPI(stepMeasureCacheDataSource.getStepMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Sends a list of [StepMeasure] to the API.
     *
     * @param stepMeasure the list of [StepMeasure] to be sent.
     * @param activityId the id of the activity that the [StepMeasure] belongs to.
     */
    suspend fun sendStepMeasureToAPI(stepMeasure : List<StepMeasure>, activityId : Int){
        try{
//            checkExistingMeasurements(activityId)
            val response = stepMeasureRemoteDataSource.sendStepMeasures(Json.encodeToString(stepMeasure), activityId)
            val code = response.code()
            if(code == 201){
                stepMeasureLocalDataSource.clearAll()
                stepMeasureCacheDataSource.clearAll()
                internetStatus.value = true
            }
            else{
                saveStepMeasuresToDB(stepMeasure)
            }
        }
        catch (exception : Exception){
            internetStatus.value = false
            Log.e("MyTag", "SendStepMeasureToAPI - " + exception.message.toString())
        }
    }

    /**
     * Saves a list of [StepMeasure] to the local database.
     *
     * @param stepMeasures the list of [StepMeasure] to be saved.
     */
    suspend fun saveStepMeasuresToDB(stepMeasures: List<StepMeasure>){
        try {
            stepMeasures.forEach{stepMeasure -> stepMeasureLocalDataSource.addStepMeasureToDB(stepMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Checks if there are any [StepMeasure] stored in the local database which
     * were not sent to the API in order to send them to the API.
     *
     * @param activityId the id of the activity that the [StepMeasure] belongs to.
     */
    suspend fun checkExistingMeasurements(activityId: Int){
        try {
            val list = stepMeasureLocalDataSource.getStepMeasureFromDB()
            if(list.isNotEmpty()){
                sendStepMeasureToAPI(list, activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

}