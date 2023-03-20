package com.victorrubia.tfg.data.repository.accelerometer_measure.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of the [AccelerometerMeasureRemoteDataSource] interface that provides a means of communicating
 * with the remote data source
 * @property tfgService The [TFGService] used to communicate with the remote data source
 * @property user The [UserCacheDataSource] used to get the user's token
 */
class AccelerometerMeasureRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : AccelerometerMeasureRemoteDataSource {

    override suspend fun sendAccelerometerMeasures(accelerometerMeasures: String, activityId : Int) =
        tfgService.addAccelerometerMeasure("Bearer ${user.getUserFromCache()!!.apiKey}", accelerometerMeasures, activityId)
}