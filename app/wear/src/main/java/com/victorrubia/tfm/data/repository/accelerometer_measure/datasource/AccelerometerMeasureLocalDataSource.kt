package com.victorrubia.tfm.data.repository.accelerometer_measure.datasource

import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure

/**
 * Interface for the local data source of the AccelerometerMeasure repository.
 */
interface AccelerometerMeasureLocalDataSource {

    /**
     * Gets the AccelerometerMeasures stored in the local data source.
     *
     * @return a list of AccelerometerMeasures.
     */
    suspend fun getAccelerometerMeasureFromDB() : List<AccelerometerMeasure>

    /**
     * Adds a AccelerometerMeasure to the local data source.
     *
     * @param accelerometerMeasure the AccelerometerMeasure to be saved.
     */
    suspend fun addAccelerometerMeasureToDB(accelerometerMeasures : AccelerometerMeasure)

    /**
     * Deletes all AccelerometerMeasures from the local data source.
     */
    suspend fun clearAll()
}