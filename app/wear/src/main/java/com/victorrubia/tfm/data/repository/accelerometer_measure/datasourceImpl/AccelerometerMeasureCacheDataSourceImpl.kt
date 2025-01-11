package com.victorrubia.tfm.data.repository.accelerometer_measure.datasourceImpl

import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureCacheDataSource

/**
 * Implementation of [AccelerometerMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class AccelerometerMeasureCacheDataSourceImpl : AccelerometerMeasureCacheDataSource {

    /**
     * Cache of Accelerometer measures.
     */
    private var accelerometerMeasures : MutableList<AccelerometerMeasure> = ArrayList()


    override suspend fun getAccelerometerMeasureFromCache(): List<AccelerometerMeasure> {
        return accelerometerMeasures
    }


    override suspend fun addAccelerometerMeasureToCache(accelerometerMeasure: AccelerometerMeasure) {
        accelerometerMeasures.add(accelerometerMeasure)
    }


    override suspend fun clearAll() {
        accelerometerMeasures.clear()
    }
}