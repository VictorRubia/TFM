package com.victorrubia.tfg.data.repository.activity_assignation

import android.os.Environment
import android.util.Log
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationCacheDataSource
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationLocalDataSource
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityAssignationRepository
import com.victorrubia.tfg.domain.repository.ActivityRepository
import java.io.File

/**
 * Implementation of the [ActivityRepository] interface that works with data sources.
 *
 * @property activityLocalDataSource the local data source
 * @property activityRemoteDataSource the remote data source
 * @property activityCacheDataSource the cache data source
 */
class ActivityAssignationRepositoryImpl(
    private val activityAssignationRemoteDataSource : ActivityAssignationRemoteDataSource,
    private val activityAssignationLocalDataSource: ActivityAssignationLocalDataSource,
    private val activityAssignationCacheDataSource: ActivityAssignationCacheDataSource
) : ActivityAssignationRepository {

    override suspend fun getActivitiesAssigned(): List<ActivityAssignation> {
        return getActivityAssignedFromCache()
    }

    override suspend fun clearActivitiesAssigned() {
        activityAssignationLocalDataSource.clearAll()
        activityAssignationCacheDataSource.clearAll()
        activityAssignationRemoteDataSource.clearAllImages()
    }

    suspend fun downloadImages(activities : List<ActivityAssignation>) {
        activityAssignationRemoteDataSource.downloadImage(activities)
    }

    /**
     * Gets the current activity from the local data source.
     *
     * @return the activity from the local data source
     */
    private suspend fun getActivityAssignedFromAPI() : List<ActivityAssignation>{
        var activity : List<ActivityAssignation> = listOf()

        try{
            Log.d("ACTIVITYASSIGNATION", "Entered to Request in the API")
            val response = activityAssignationRemoteDataSource.getActivitiesAssigned()
            val body = response.body()
            if(body != null){
                activity = body
                downloadImages(activity)
                Log.d("ACTIVITYASSIGNATION", "Response from API: $activity")
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return activity
    }

    /**
     * Gets the current activity from the local data source.
     *
     * @return the activity from the local data source
     */
    private suspend fun getActivityAssignedFromDB() : List<ActivityAssignation>{
        var activity : List<ActivityAssignation> = listOf()

        Log.d("ACTIVITYASSIGNATION", "Entered to Request in the DB")

        try{
            activity = activityAssignationLocalDataSource.getActivityAssignationFromDB()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        if(activity.isNotEmpty()){
            return activity
        }
        else{
            activity = getActivityAssignedFromAPI()
            if(activity.isNotEmpty()){
                for (activityAssignation in activity) {
                    activityAssignationLocalDataSource.saveActivityAssignationToDB(activityAssignation)
                }
            }
        }

        return activity
    }

    /**
     * Gets the current activity from the cache data source.
     *
     * @return the activity from the cache data source
     */
    private suspend fun getActivityAssignedFromCache() : List<ActivityAssignation>{
        var activity : List<ActivityAssignation> = listOf()

        Log.d("ACTIVITYASSIGNATION", "Entered to Request in the Cache")

        try{
            activity = activityAssignationCacheDataSource.getActivityAssignationFromCache()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        if(activity.isNotEmpty()){
            return activity
        }
        else{
            activity = getActivityAssignedFromDB()
            if(activity.isNotEmpty()) {
                for (activityAssignation in activity) {
                    activityAssignationCacheDataSource.saveActivityAssignationToCache(activityAssignation)
                }
            }
        }

        return activity
    }
}