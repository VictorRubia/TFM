package com.victorrubia.tfm.data.repository.accelerometer_measure.datasource

import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure

/**
 * Interface for the cache data source of the accelerometer measure repository.
 */
interface AccelerometerMeasureCacheDataSource {
    /**
     * Gets the accelerometer measures from the cache
     * @return a list of [AccelerometerMeasure] objects
     */
    suspend fun getAccelerometerMeasureFromCache(): List<AccelerometerMeasure>
    /**
     * Adds the accelerometer measure to the cache
     * @param accelerometerMeasure a [AccelerometerMeasure] object
     */
    suspend fun addAccelerometerMeasureToCache(accelerometerMeasure: AccelerometerMeasure)

    /**
     * Clears the cache from the accelerometer measures
     */
    suspend fun clearAll()
}