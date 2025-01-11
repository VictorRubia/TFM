package com.victorrubia.tfm.data.repository.step_measure.datasource

import com.victorrubia.tfm.data.model.step_measure.StepMeasure

/**
 * Interface for the local data source of the StepMeasure repository.
 */
interface StepMeasureLocalDataSource {

    /**
     * Gets the StepMeasures stored in the local data source.
     *
     * @return a list of StepMeasures.
     */
    suspend fun getStepMeasureFromDB() : List<StepMeasure>

    /**
     * Adds a StepMeasure to the local data source.
     *
     * @param stepMeasure the StepMeasure to be saved.
     */
    suspend fun addStepMeasureToDB(stepMeasures : StepMeasure)

    /**
     * Deletes all StepMeasures from the local data source.
     */
    suspend fun clearAll()
}