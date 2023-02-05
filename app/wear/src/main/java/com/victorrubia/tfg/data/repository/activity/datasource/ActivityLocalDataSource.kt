package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity

/**
 * Interface for the local data source of the activities
 */
interface ActivityLocalDataSource {
    /**
     * Gets the current activity from the local data source
     * @return current activity
     */
    suspend fun getActivityFromDB() : Activity

    /**
     * Saves the current activity in the local data source
     * @param activity activity to save
     */
    suspend fun saveActivityToDB(activity: Activity)

    /**
     * Deletes the current activity from the local data source
     */
    suspend fun clearAll()
}