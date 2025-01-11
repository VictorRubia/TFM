package com.victorrubia.tfm.data.repository.significantMov_measure.datasource

import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure

/**
 * Interface for the cache data source of the significantMov measure repository.
 */
interface SignificantMovMeasureCacheDataSource {
    /**
     * Gets the significantMov measures from the cache
     * @return a list of [SignificantMovMeasure] objects
     */
    suspend fun getSignificantMovMeasureFromCache(): List<SignificantMovMeasure>
    /**
     * Adds the significantMov measure to the cache
     * @param significantMovMeasure a [SignificantMovMeasure] object
     */
    suspend fun addSignificantMovMeasureToCache(significantMovMeasure: SignificantMovMeasure)

    /**
     * Clears the cache from the significantMov measures
     */
    suspend fun clearAll()
}