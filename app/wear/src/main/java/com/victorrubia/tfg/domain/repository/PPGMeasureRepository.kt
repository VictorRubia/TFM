package com.victorrubia.tfg.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

/**
 * Repository interface for PPG measures
 */
interface PPGMeasureRepository {

    /**
     * Saves PPG Measures to the repository.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param ppgMeasure PPG Measure to be saved
     * @param activityId Activity id to which the PPG Measure belongs
     * @return True if the PPG Measure was saved successfully, false otherwise
     */
    suspend fun savePPGMeasure(ppgMeasure : PPGMeasure, activityId : Int) : MutableState<Boolean>

    /**
     * Gets all PPG Measures from the repository and sends them to the backend server.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param activityId Activity id to which the PPG Measures belong
     */
    suspend fun endPPGMeasure(activityId : Int)
}