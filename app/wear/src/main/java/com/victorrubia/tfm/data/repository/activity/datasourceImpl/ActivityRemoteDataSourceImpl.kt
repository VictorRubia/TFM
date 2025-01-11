package com.victorrubia.tfm.data.repository.activity.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource
import retrofit2.Response

/**
 * Implementation of the [ActivityRemoteDataSource] interface that provides a means of communicating
 * with the remote data source to retrieve data.
 * @property tfmService The [TFMService] used to communicate with the remote data source.
 * @property user The [UserCacheDataSource] used to cache the user's token.
 */
class ActivityRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : ActivityRemoteDataSource {


    override suspend fun newActivity(activityId: Int,
                                     startTimestamp: String) : Response<Activity> =
        tfmService.newActivity("Bearer ${user.getUserFromCache()!!.apiKey}", activityId, startTimestamp)


    override suspend fun endActivity(activityId: Int, endTimestamp: String): Response<Activity> =
        tfmService.endActivity("Bearer ${user.getUserFromCache()!!.apiKey}", activityId, endTimestamp)
}