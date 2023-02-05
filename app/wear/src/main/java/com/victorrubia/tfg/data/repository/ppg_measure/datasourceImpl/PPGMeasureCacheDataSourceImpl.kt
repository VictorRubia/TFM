package com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource

/**
 * Implementation of [PPGMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class PPGMeasureCacheDataSourceImpl : PPGMeasureCacheDataSource {

    /**
     * Cache of PPG measures.
     */
    private var ppgMeasures : MutableList<PPGMeasure> = ArrayList()


    override suspend fun getPPGMeasureFromCache(): List<PPGMeasure> {
        return ppgMeasures
    }


    override suspend fun addPPGMeasureToCache(ppgMeasure: PPGMeasure) {
        ppgMeasures.add(ppgMeasure)
    }


    override suspend fun clearAll() {
        ppgMeasures.clear()
    }
}