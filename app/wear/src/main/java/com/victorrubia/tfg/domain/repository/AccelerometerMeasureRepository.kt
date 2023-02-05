package com.victorrubia.tfg.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfg.data.model.accelerometer_measure.AccelerometerMeasure

/**
 * Repository interface for Accelerometer measures
 */
interface AccelerometerMeasureRepository {

    /**
     * Saves Accelerometer Measures to the repository.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param accelerometerMeasure Accelerometer Measure to be saved
     * @param activityId Activity id to which the Accelerometer Measure belongs
     * @return True if the Accelerometer Measure was saved successfully, false otherwise
     */
    suspend fun saveAccelerometerMeasure(accelerometerMeasure : AccelerometerMeasure, activityId : Int) : MutableState<Boolean>

    /**
     * Gets all Accelerometer Measures from the repository and sends them to the backend server.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param activityId Activity id to which the Accelerometer Measures belong
     */
    suspend fun endAccelerometerMeasure(activityId : Int)
}