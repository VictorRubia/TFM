package com.victorrubia.tfg.data.repository.activity_assignation.datasourceImpl

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationCacheDataSource

class ActivityAssignationCacheDataSourceImpl : ActivityAssignationCacheDataSource {

    /**
     * Cache of current activity
     */
    private var activity : MutableList<ActivityAssignation> = mutableListOf()
    
    override suspend fun getActivityAssignationFromCache(): List<ActivityAssignation> {
        return activity
    }


    override suspend fun saveActivityAssignationToCache(activity: ActivityAssignation) {
        this.activity.add(activity)
    }


    override suspend fun clearAll() {
        this.activity.clear()
    }

}