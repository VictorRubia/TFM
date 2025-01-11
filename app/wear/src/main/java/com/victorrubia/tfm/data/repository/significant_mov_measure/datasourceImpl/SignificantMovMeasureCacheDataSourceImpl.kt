package com.victorrubia.tfm.data.repository.significantMov_measure.datasourceImpl

import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureCacheDataSource

/**
 * Implementation of [SignificantMovMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class SignificantMovMeasureCacheDataSourceImpl : SignificantMovMeasureCacheDataSource {

    /**
     * Cache of SignificantMov measures.
     */
    private var significantMovMeasures : MutableList<SignificantMovMeasure> = ArrayList()


    override suspend fun getSignificantMovMeasureFromCache(): List<SignificantMovMeasure> {
        return significantMovMeasures
    }


    override suspend fun addSignificantMovMeasureToCache(significantMovMeasure: SignificantMovMeasure) {
        significantMovMeasures.add(significantMovMeasure)
    }


    override suspend fun clearAll() {
        significantMovMeasures.clear()
    }
}