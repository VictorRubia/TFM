package com.victorrubia.tfg.data.repository.ppg_measure.datasource

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

/**
 * Interface for the local data source of the PPGMeasure repository.
 */
interface PPGMeasureLocalDataSource {

    /**
     * Gets the PPGMeasures stored in the local data source.
     *
     * @return a list of PPGMeasures.
     */
    suspend fun getPPGMeasureFromDB() : List<PPGMeasure>

    /**
     * Adds a PPGMeasure to the local data source.
     *
     * @param ppgMeasure the PPGMeasure to be saved.
     */
    suspend fun addPPGMeasureToDB(ppgMeasures : PPGMeasure)

    /**
     * Deletes all PPGMeasures from the local data source.
     */
    suspend fun clearAll()
}