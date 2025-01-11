package com.victorrubia.tfm.data.repository.step_measure.datasourceImpl

import com.victorrubia.tfm.data.model.step_measure.StepMeasure
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureCacheDataSource

/**
 * Implementation of [StepMeasureCacheDataSource] interface for retrieving and storing data in cache.
 */
class StepMeasureCacheDataSourceImpl : StepMeasureCacheDataSource {

    /**
     * Cache of Step measures.
     */
    private var stepMeasures : MutableList<StepMeasure> = ArrayList()


    override suspend fun getStepMeasureFromCache(): List<StepMeasure> {
        return stepMeasures
    }


    override suspend fun addStepMeasureToCache(stepMeasure: StepMeasure) {
        stepMeasures.add(stepMeasure)
    }


    override suspend fun clearAll() {
        stepMeasures.clear()
    }
}