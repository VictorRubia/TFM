package com.victorrubia.tfm.data.repository.activity_assignation.datasource

import com.victorrubia.tfm.data.model.activity_repository.ActivityAssignation


/**
 * Interface for the cache data source of the activities repository.
 */
interface ActivityAssignationCacheDataSource {
    /**
     * Gets the current activity from the cache
     * @return current activity
     */
    suspend fun getActivityAssignationFromCache() : List<ActivityAssignation>

    /**
     * Saves the current activity in the cache
     * @param activity activity to save
     */
    suspend fun saveActivityAssignationToCache(activity: ActivityAssignation)

    /**
     * Clears the current activity from the cache
     */
    suspend fun clearAll()
}