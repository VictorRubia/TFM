package com.victorrubia.tfm.data.repository.gps_measure.datasource

import retrofit2.Response

/**
 * Interface for remote data sources of the GPS Measure repository.
 */
interface GPSMeasureRemoteDataSource {

    /**
     * Sends a list of GPS measures to the backend server for user's current activity
     *
     * @param gpsMeasures List of GPS measures to be sent
     * @param activityId Id of the activity to which the measures belong
     * @return Response from the server
     */
    suspend fun sendGPSMeasures(gpsMeasures: String, activityId : Int) : Response<*>

}