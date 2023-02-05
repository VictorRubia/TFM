package com.victorrubia.tfg.data.repository.significantMov_measure.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [SignificantMovMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfgService The [TFGService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class SignificantMovMeasureRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : SignificantMovMeasureRemoteDataSource {


    override suspend fun sendSignificantMovMeasures(significantMovMeasures: String, activityId : Int) =
        tfgService.addSignificantMovMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", significantMovMeasures, activityId)
}