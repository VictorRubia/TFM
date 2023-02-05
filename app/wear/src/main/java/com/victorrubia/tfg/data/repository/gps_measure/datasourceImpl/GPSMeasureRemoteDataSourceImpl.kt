package com.victorrubia.tfg.data.repository.gps_measure.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [GPSMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfgService The [TFGService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class GPSMeasureRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : GPSMeasureRemoteDataSource {


    override suspend fun sendGPSMeasures(gpsMeasures: String, activityId : Int) =
        tfgService.addGPSMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", gpsMeasures, activityId)
}