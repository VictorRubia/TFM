package com.victorrubia.tfg.data.repository.gps_measure.datasource

import com.victorrubia.tfg.data.model.gps_measure.GPSMeasure
/**
 * Interface for the cache data source of the gps measure repository.
 */
interface GPSMeasureCacheDataSource {
    /**
     * Gets the gps measures from the cache
     * @return a list of [GPSMeasure] objects
     */
    suspend fun getGPSMeasureFromCache(): List<GPSMeasure>
    /**
     * Adds the gps measure to the cache
     * @param gpsMeasure a [GPSMeasure] object
     */
    suspend fun addGPSMeasureToCache(gpsMeasure: GPSMeasure)

    /**
     * Clears the cache from the gps measures
     */
    suspend fun clearAll()
}