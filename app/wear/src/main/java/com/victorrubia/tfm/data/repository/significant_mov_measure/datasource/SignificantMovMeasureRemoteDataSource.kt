package com.victorrubia.tfm.data.repository.significantMov_measure.datasource

import retrofit2.Response

/**
 * Interface for remote data sources of the SignificantMov Measure repository.
 */
interface SignificantMovMeasureRemoteDataSource {

    /**
     * Sends a list of SignificantMov measures to the backend server for user's current activity
     *
     * @param significantMovMeasures List of SignificantMov measures to be sent
     * @param activityId Id of the activity to which the measures belong
     * @return Response from the server
     */
    suspend fun sendSignificantMovMeasures(significantMovMeasures: String, activityId : Int) : Response<*>

}