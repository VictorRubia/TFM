package com.victorrubia.tfm.data.repository.accelerometer_measure.datasourceImpl

import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [AccelerometerMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfmService The [TFMService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class AccelerometerMeasureRemoteDataSourceImpl(
    private val tfmService: TFMService,
    private val user : UserCacheDataSource
) : AccelerometerMeasureRemoteDataSource {

    override suspend fun sendAccelerometerMeasures(accelerometerMeasures: String, activityId : Int) =
        tfmService.addAccelerometerMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", accelerometerMeasures, activityId)
}