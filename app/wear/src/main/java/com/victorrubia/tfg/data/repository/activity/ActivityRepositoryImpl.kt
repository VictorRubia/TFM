package com.victorrubia.tfg.data.repository.activity

import android.util.Log
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.domain.repository.ActivityRepository
import java.time.Instant
import java.time.ZoneId

/**
 * Implementation of the [ActivityRepository] interface that works with data sources.
 *
 * @property activityLocalDataSource the local data source
 * @property activityRemoteDataSource the remote data source
 * @property activityCacheDataSource the cache data source
 */
class ActivityRepositoryImpl(
    private val activityRemoteDataSource : ActivityRemoteDataSource,
    private val activityLocalDataSource: ActivityLocalDataSource,
    private val activityCacheDataSource: ActivityCacheDataSource
) : ActivityRepository {

    override suspend fun newActivity(name: String, startTimestamp: String): Activity {
        return newActivityAPI(name, startTimestamp)
    }

    override suspend fun getCurrentActivity(): Activity? {
        return getActivityFromCache()
    }

    override suspend fun endActivity(): Activity? {
        return endActivityAPI()
    }

    /**
     * Starts a new activity in the API.
     *
     * @param name the name of the activity
     * @param startTimestamp the start timestamp of the activity
     * @return the activity created
     */
    suspend fun newActivityAPI(name: String, startTimestamp: String) : Activity {
        lateinit var activity : Activity

        try{
            val response = activityRemoteDataSource.newActivity(name, startTimestamp)
            val body = response.body()
            if(body != null){
                activity = body
                activityLocalDataSource.saveActivityToDB(activity)
                activityCacheDataSource.saveActivityToCache(activity)
            }
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return activity
    }

    /**
     * Ends the current activity in the API.
     *
     * @return the activity ended
     */
    suspend fun endActivityAPI() : Activity? {
        val activity: Activity? = activityCacheDataSource.getActivityFromCache()

        try {
            val response = activity?.id?.let { activityRemoteDataSource.endActivity(it, Instant.now().atZone(ZoneId.of("Europe/Madrid")).toString()) }
            val body = response?.body()
            if(body != null){
                activityLocalDataSource.clearAll()
                activityCacheDataSource.clearAll()
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
    suspend fun getActivityFromDB() : Activity?{
        var activity : Activity? = null

        try{
            activity = activityLocalDataSource.getActivityFromDB()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return activity
    }

    /**
     * Gets the current activity from the cache data source.
     *
     * @return the activity from the cache data source
     */
    suspend fun getActivityFromCache() : Activity?{
        var activity : Activity? = null

        try{
            activity = activityCacheDataSource.getActivityFromCache()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        if(activity != null){
            return activity
        }
        else{
            activity = getActivityFromDB()
            if(activity != null) activityCacheDataSource.saveActivityToCache(activity)
        }

        return activity
    }
}