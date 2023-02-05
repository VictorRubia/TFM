package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.activity.Activity

/**
 * Activity repository interface
 */
interface ActivityRepository {

    /**
     * Starts a new activity on the backend server and saves it in the local database.
     *
     * @param name Name of the activity
     * @param startTimestamp Start timestamp of the activity
     * @return The created activity
     */
    suspend fun newActivity(name : String, startTimestamp : String) : Activity

    /**
     * Gets the current activity which is taking place at the moment
     * from local data sources. When no activity is taking place, it
     * returns null.
     *
     * @return The current activity
     */
    suspend fun getCurrentActivity() : Activity?

    /**
     * Stop the current activity and save it on the backend server.
     * Therefore it clears the local activity repositories
     *
     * @return The stopped activity
     */
    suspend fun endActivity() : Activity?

}