package com.victorrubia.tfm.data.repository.gps_measure.datasource

import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure

/**
 * Interface for the local data source of the GPSMeasure repository.
 */
interface GPSMeasureLocalDataSource {

    /**
     * Gets the GPSMeasures stored in the local data source.
     *
     * @return a list of GPSMeasures.
     */
    suspend fun getGPSMeasureFromDB() : List<GPSMeasure>

    /**
     * Adds a GPSMeasure to the local data source.
     *
     * @param gpsMeasure the GPSMeasure to be saved.
     */
    suspend fun addGPSMeasureToDB(gpsMeasures : GPSMeasure)

    /**
     * Deletes all GPSMeasures from the local data source.
     */
    suspend fun clearAll()
}