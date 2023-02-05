package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import retrofit2.Response

/**
 * Interface for remote data sources of the PPG Measure repository.
 */
interface PPGMeasureRemoteDataSource {

    /**
     * Sends a list of PPG measures to the backend server for user's current activity
     *
     * @param ppgMeasures List of PPG measures to be sent
     * @param activityId Id of the activity to which the measures belong
     * @return Response from the server
     */
    suspend fun sendPPGMeasures(ppgMeasures: String, activityId : Int) : Response<*>

}