package com.victorrubia.tfm.data.repository.gps_measure.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [GPSMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class GPSMeasureRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : GPSMeasureRemoteDataSource {


    override suspend fun sendGPSMeasures(gpsMeasures: String, activityId : Int) =
        tfmService.addGPSMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", gpsMeasures, activityId)
}