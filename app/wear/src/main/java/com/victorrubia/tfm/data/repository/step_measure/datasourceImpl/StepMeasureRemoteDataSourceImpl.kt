package com.victorrubia.tfm.data.repository.step_measure.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [StepMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class StepMeasureRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : StepMeasureRemoteDataSource {


    override suspend fun sendStepMeasures(stepMeasures: String, activityId : Int) =
        tfmService.addStepMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", stepMeasures, activityId)
}