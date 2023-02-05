package com.victorrubia.tfg.data.repository.activity.datasource

import com.victorrubia.tfg.data.model.activity.Activity

/**
 * Interface for the cache data source of the activities repository.
 */
interface ActivityCacheDataSource {
    /**
     * Gets the current activity from the cache
     * @return current activity
     */
    suspend fun getActivityFromCache() : Activity?

    /**
     * Saves the current activity in the cache
     * @param activity activity to save
     */
    suspend fun saveActivityToCache(activity: Activity)

    /**
     * Clears the current activity from the cache
     */
    suspend fun clearAll()
}