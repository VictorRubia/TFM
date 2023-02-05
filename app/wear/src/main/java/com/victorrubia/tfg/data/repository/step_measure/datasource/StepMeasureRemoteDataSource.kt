package com.victorrubia.tfg.data.repository.step_measure.datasource

import retrofit2.Response

/**
 * Interface for remote data sources of the Step Measure repository.
 */
interface StepMeasureRemoteDataSource {

    /**
     * Sends a list of Step measures to the backend server for user's current activity
     *
     * @param stepMeasures List of Step measures to be sent
     * @param activityId Id of the activity to which the measures belong
     * @return Response from the server
     */
    suspend fun sendStepMeasures(stepMeasures: String, activityId : Int) : Response<*>

}