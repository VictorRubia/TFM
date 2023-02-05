package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

/**
 * Interface for the cache data source of the ppg measure repository.
 */
interface PPGMeasureCacheDataSource {
    /**
     * Gets the ppg measures from the cache
     * @return a list of [PPGMeasure] objects
     */
    suspend fun getPPGMeasureFromCache(): List<PPGMeasure>
    /**
     * Adds the ppg measure to the cache
     * @param ppgMeasure a [PPGMeasure] object
     */
    suspend fun addPPGMeasureToCache(ppgMeasure: PPGMeasure)

    /**
     * Clears the cache from the ppg measures
     */
    suspend fun clearAll()
}