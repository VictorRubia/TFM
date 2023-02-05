package com.victorrubia.tfg.data.repository.step_measure.datasource

import com.victorrubia.tfg.data.model.step_measure.StepMeasure
/**
 * Interface for the cache data source of the step measure repository.
 */
interface StepMeasureCacheDataSource {
    /**
     * Gets the step measures from the cache
     * @return a list of [StepMeasure] objects
     */
    suspend fun getStepMeasureFromCache(): List<StepMeasure>
    /**
     * Adds the step measure to the cache
     * @param stepMeasure a [StepMeasure] object
     */
    suspend fun addStepMeasureToCache(stepMeasure: StepMeasure)

    /**
     * Clears the cache from the step measures
     */
    suspend fun clearAll()
}