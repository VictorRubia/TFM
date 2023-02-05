package com.victorrubia.tfg.data.repository.accelerometer_measure

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfg.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfg.domain.repository.AccelerometerMeasureRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of the [AccelerometerMeasureRepository] interface that works with data sources.
 *
 * @property accelerometerMeasureCacheDataSource the cache data source that is used to store and retrieve data.
 * @property accelerometerMeasureLocalDataSource the local data source that is used to store and retrieve data.
 * @property accelerometerMeasureRemoteDataSource the remote data source that is used to store and retrieve data.
 */
class AccelerometerMeasureRepositoryImpl(
    private val accelerometerMeasureRemoteDataSource: AccelerometerMeasureRemoteDataSource,
    private val accelerometerMeasureLocalDataSource: AccelerometerMeasureLocalDataSource,
    private val accelerometerMeasureCacheDataSource: AccelerometerMeasureCacheDataSource,
) : AccelerometerMeasureRepository {

    /**
     * Variable that stores number of times a measurement has taken place.
     */
    private var count = 0
    /**
     * The [MutableState] that stores the status of internet connection.
     */
    private val internetStatus = mutableStateOf(true)


    override suspend fun saveAccelerometerMeasure(accelerometerMeasure: AccelerometerMeasure, activityId: Int): MutableState<Boolean> {

        try {
            accelerometerMeasureCacheDataSource.addAccelerometerMeasureToCache(accelerometerMeasure)
            count++
            if(count == 2000){
                count = 0
                sendAccelerometerMeasureToAPI(accelerometerMeasureCacheDataSource.getAccelerometerMeasureFromCache(), activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return internetStatus
    }


    override suspend fun endAccelerometerMeasure(activityId : Int) {
        try {
            sendAccelerometerMeasureToAPI(accelerometerMeasureCacheDataSource.getAccelerometerMeasureFromCache(), activityId)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Sends a list of [AccelerometerMeasure] to the API.
     *
     * @param accelerometerMeasure the list of [AccelerometerMeasure] to be sent.
     * @param activityId the id of the activity that the [AccelerometerMeasure] belongs to.
     */
    suspend fun sendAccelerometerMeasureToAPI(accelerometerMeasure : List<AccelerometerMeasure>, activityId : Int){
        try{
            checkExistingMeasurements(activityId)
            val response = accelerometerMeasureRemoteDataSource.sendAccelerometerMeasures(Json.encodeToString(accelerometerMeasure), activityId)
            val code = response.code()
            if(code == 201){
                accelerometerMeasureLocalDataSource.clearAll()
                accelerometerMeasureCacheDataSource.clearAll()
                internetStatus.value = true
            }
            else{
                saveAccelerometerMeasuresToDB(accelerometerMeasure)
            }
        }
        catch (exception : Exception){
            internetStatus.value = false
            Log.e("MyTag", "SendAccelerometerMeasureToAPI - " + exception.message.toString())
        }
    }

    /**
     * Saves a list of [AccelerometerMeasure] to the local database.
     *
     * @param accelerometerMeasures the list of [AccelerometerMeasure] to be saved.
     */
    suspend fun saveAccelerometerMeasuresToDB(accelerometerMeasures: List<AccelerometerMeasure>){
        try {
            accelerometerMeasures.forEach{accelerometerMeasure -> accelerometerMeasureLocalDataSource.addAccelerometerMeasureToDB(accelerometerMeasure)}
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Checks if there are any [AccelerometerMeasure] stored in the local database which
     * were not sent to the API in order to send them to the API.
     *
     * @param activityId the id of the activity that the [AccelerometerMeasure] belongs to.
     */
    suspend fun checkExistingMeasurements(activityId: Int){
        try {
            val list = accelerometerMeasureLocalDataSource.getAccelerometerMeasureFromDB()
            if(list.isNotEmpty()){
                sendAccelerometerMeasureToAPI(list, activityId)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

}