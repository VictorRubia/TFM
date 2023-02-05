package com.victorrubia.tfg.data.repository.step_measure.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [StepMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfgService The [TFGService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class StepMeasureRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : StepMeasureRemoteDataSource {


    override suspend fun sendStepMeasures(stepMeasures: String, activityId : Int) =
        tfgService.addStepMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", stepMeasures, activityId)
}