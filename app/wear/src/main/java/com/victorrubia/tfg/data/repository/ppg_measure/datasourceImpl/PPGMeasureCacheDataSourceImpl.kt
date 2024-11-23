package com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl

import android.util.Log
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Implementation of [PPGMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class PPGMeasureCacheDataSourceImpl : PPGMeasureCacheDataSource {

    private val mutex = Mutex()

    /**
     * Cache of PPG measures.
     */
    private val ppgMeasures: MutableList<PPGMeasure> = CopyOnWriteArrayList()

    override suspend fun getPPGMeasureFromCache(): List<PPGMeasure> {
        return ppgMeasures.toList() // Return a copy to prevent external modification
    }

    override suspend fun addPPGMeasureToCache(ppgMeasure: PPGMeasure) {
        mutex.withLock {
            ppgMeasures.add(ppgMeasure)
        }
    }

    override suspend fun clearAll() {
        ppgMeasures.clear()
    }
}