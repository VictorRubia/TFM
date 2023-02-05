package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import com.victorrubia.tfg.data.db.ActivityDao
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [ActivityLocalDataSource] interface for retrieving data from the local data source
 * (i.e. Room database).
 *
 * @property activityDao The DAO for the [Activity] table.
 */
class ActivityLocalDataSourceImpl(
    private val activityDao : ActivityDao
) : ActivityLocalDataSource{


    override suspend fun getActivityFromDB(): Activity {
        return activityDao.getActivity()
    }


    override suspend fun saveActivityToDB(activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            activityDao.saveActivity(activity)
        }
    }


    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            activityDao.deleteActivity()
        }
    }

}