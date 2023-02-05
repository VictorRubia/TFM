package com.victorrubia.tfg.data.repository.activity.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import retrofit2.Response

/**
 * Implementation of the [ActivityRemoteDataSource] interface that provides a means of communicating
 * with the remote data source to retrieve data.
 * @property tfgService The [TFGService] used to communicate with the remote data source.
 * @property user The [UserCacheDataSource] used to cache the user's token.
 */
class ActivityRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : ActivityRemoteDataSource {


    override suspend fun newActivity(name: String,
                                     startTimestamp: String) : Response<Activity> =
        tfgService.newActivity("Bearer ${user.getUserFromCache()!!.apiKey}", name, startTimestamp)


    override suspend fun endActivity(activityId: Int, endTimestamp: String): Response<Activity> =
        tfgService.endActivity("Bearer ${user.getUserFromCache()!!.apiKey}", activityId, endTimestamp)
}