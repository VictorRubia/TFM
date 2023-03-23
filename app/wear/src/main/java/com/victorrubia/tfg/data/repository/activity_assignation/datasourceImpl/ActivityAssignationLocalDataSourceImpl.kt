package com.victorrubia.tfg.data.repository.activity_assignation.datasourceImpl

import com.victorrubia.tfg.data.db.ActivityAssignationDao
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [ActivityLocalDataSource] interface for retrieving data from the local data source
 * (i.e. Room database).
 *
 * @property activityAssignationDao The DAO for the [Activity] table.
 */
class ActivityAssignationLocalDataSourceImpl(
    private val activityAssignationDao : ActivityAssignationDao
) : ActivityAssignationLocalDataSource {

    override suspend fun getActivityAssignationFromDB(): List<ActivityAssignation> {
        return activityAssignationDao.getActivityAssignation()
    }


    override suspend fun saveActivityAssignationToDB(activity: ActivityAssignation) {
        CoroutineScope(Dispatchers.IO).launch {
            activityAssignationDao.saveActivityAssignation(activity)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            activityAssignationDao.deleteActivityAssignation()
        }
    }

}