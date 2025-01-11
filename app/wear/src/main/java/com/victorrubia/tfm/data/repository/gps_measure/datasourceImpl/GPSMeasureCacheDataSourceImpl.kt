package com.victorrubia.tfm.data.repository.gps_measure.datasourceImpl

import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureCacheDataSource

/**
 * Implementation of [GPSMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class GPSMeasureCacheDataSourceImpl : GPSMeasureCacheDataSource {

    /**
     * Cache of GPS measures.
     */
    private var gpsMeasures : MutableList<GPSMeasure> = ArrayList()


    override suspend fun getGPSMeasureFromCache(): List<GPSMeasure> {
        return gpsMeasures
    }


    override suspend fun addGPSMeasureToCache(gpsMeasure: GPSMeasure) {
        gpsMeasures.add(gpsMeasure)
    }


    override suspend fun clearAll() {
        gpsMeasures.clear()
    }
}