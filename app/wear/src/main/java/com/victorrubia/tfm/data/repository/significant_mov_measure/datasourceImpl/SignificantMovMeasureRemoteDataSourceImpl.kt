package com.victorrubia.tfm.data.repository.significantMov_measure.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [SignificantMovMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class SignificantMovMeasureRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : SignificantMovMeasureRemoteDataSource {


    override suspend fun sendSignificantMovMeasures(significantMovMeasures: String, activityId : Int) =
        tfmService.addSignificantMovMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", significantMovMeasures, activityId)
}