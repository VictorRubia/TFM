package com.victorrubia.tfg.data.repository.activity_assignation.datasource

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation

/**
 * Interface for the local data source of the activities
 */
interface ActivityAssignationLocalDataSource {

    /**
     * Gets the current activity from the local data source
     * @return current activity
     */
    suspend fun getActivityAssignationFromDB() : List<ActivityAssignation>

    /**
     * Saves the current activity in the local data source
     * @param activity activity to save
     */
    suspend fun saveActivityAssignationToDB(activity: ActivityAssignation)

    /**
     * Deletes the current activity from the local data source
     */
    suspend fun clearAll()
}