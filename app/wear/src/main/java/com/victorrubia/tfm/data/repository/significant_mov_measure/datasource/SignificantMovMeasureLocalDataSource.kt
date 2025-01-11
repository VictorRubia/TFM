package com.victorrubia.tfm.data.repository.significantMov_measure.datasource

import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure


/**
 * Interface for the local data source of the SignificantMovMeasure repository.
 */
interface SignificantMovMeasureLocalDataSource {

    /**
     * Gets the SignificantMovMeasures stored in the local data source.
     *
     * @return a list of SignificantMovMeasures.
     */
    suspend fun getSignificantMovMeasureFromDB() : List<SignificantMovMeasure>

    /**
     * Adds a SignificantMovMeasure to the local data source.
     *
     * @param significantMovMeasure the SignificantMovMeasure to be saved.
     */
    suspend fun addSignificantMovMeasureToDB(significantMovMeasures : SignificantMovMeasure)

    /**
     * Deletes all SignificantMovMeasures from the local data source.
     */
    suspend fun clearAll()
}