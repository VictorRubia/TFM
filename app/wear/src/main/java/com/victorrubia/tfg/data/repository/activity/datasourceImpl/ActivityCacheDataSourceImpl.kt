package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource

/**
 * Implementation of [ActivityCacheDataSource] interface for retrieving and storing data from the cache data source
 */
class ActivityCacheDataSourceImpl : ActivityCacheDataSource {

    /**
     * Cache of current activity
     */
    private var activity : Activity? = null
    
    override suspend fun getActivityFromCache(): Activity? {
        return activity
    }


    override suspend fun saveActivityToCache(activity: Activity) {
        this.activity = activity
    }


    override suspend fun clearAll() {
        this.activity = null
    }

}