package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity
import retrofit2.Response

/**
 * Interface for remote data source of the activities
 */
interface ActivityRemoteDataSource {

    /**
     * Starts a new activity for the current user
     *
     * @param name Name of the activity
     * @param startTimestamp Start timestamp of the activity
     * @return The new Activity object
     */
    suspend fun newActivity(name : String, startTimestamp : String) : Response<Activity>

    /**
     * Stops the current activity for the current user
     *
     * @param activityId Id of the activity to stop
     * @param endTimestamp End timestamp of the activity
     * @return The updated Activity object
     */
    suspend fun endActivity(activityId : Int, endTimestamp: String) : Response<Activity>

}