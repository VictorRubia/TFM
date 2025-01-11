package com.victorrubia.tfm.data.repository.accelerometer_measure.datasource

import retrofit2.Response

/**
 * Interface for remote data sources of the Accelerometer Measure repository.
 */
interface AccelerometerMeasureRemoteDataSource {

    /**
     * Sends a list of Accelerometer measures to the backend server for user's current activity
     *
     * @param accelerometerMeasures List of Accelerometer measures to be sent
     * @param activityId Id of the activity to which the measures belong
     * @return Response from the server
     */
    suspend fun sendAccelerometerMeasures(accelerometerMeasures: String, activityId : Int) : Response<*>

}