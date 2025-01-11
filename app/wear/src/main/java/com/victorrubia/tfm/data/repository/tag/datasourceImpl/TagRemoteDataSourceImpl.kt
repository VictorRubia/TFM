package com.victorrubia.tfm.data.repository.tag.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.model.tag.Tag
import com.victorrubia.tfm.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource
import retrofit2.Response
import java.util.*

/**
 * Implementation of [TagRemoteDataSource] for that provides a means of communicating
 * with the remote data source that stores tag data.
 *
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token.
 */
class TagRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : TagRemoteDataSource{


    override suspend fun addTag(tag: String, datetime: Date, activityId: Int): Response<Tag> =
        tfmService.addTag("Bearer ${user.getUserFromCache()!!.apiKey}", tag, datetime, activityId)
}