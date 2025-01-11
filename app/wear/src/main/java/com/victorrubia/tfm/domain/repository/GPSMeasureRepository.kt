package com.victorrubia.tfm.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure

/**
 * Repository interface for GPS measures
 */
interface GPSMeasureRepository {

    /**
     * Saves GPS Measures to the repository.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param gpsMeasure GPS Measure to be saved
     * @param activityId Activity id to which the GPS Measure belongs
     * @return True if the GPS Measure was saved successfully, false otherwise
     */
    suspend fun saveGPSMeasure(gpsMeasure : GPSMeasure, activityId : Int) : MutableState<Boolean>

    /**
     * Gets all GPS Measures from the repository and sends them to the backend server.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param activityId Activity id to which the GPS Measures belong
     */
    suspend fun endGPSMeasure(activityId : Int)
}