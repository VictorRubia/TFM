package com.victorrubia.tfm.data.repository.ppg_measure.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [PPGMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class PPGMeasureRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : PPGMeasureRemoteDataSource {


    override suspend fun sendPPGMeasures(ppgMeasures: String, activityId : Int) =
        tfmService.addPPGMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", ppgMeasures, activityId)
}