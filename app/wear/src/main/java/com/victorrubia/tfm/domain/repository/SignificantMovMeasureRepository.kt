package com.victorrubia.tfm.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure

/**
 * Repository interface for SignificantMov measures
 */
interface SignificantMovMeasureRepository {

    /**
     * Saves SignificantMov Measures to the repository.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param significantMovMeasure SignificantMov Measure to be saved
     * @param activityId Activity id to which the SignificantMov Measure belongs
     * @return True if the SignificantMov Measure was saved successfully, false otherwise
     */
    suspend fun saveSignificantMovMeasure(significantMovMeasure : SignificantMovMeasure, activityId : Int) : MutableState<Boolean>

    /**
     * Gets all SignificantMov Measures from the repository and sends them to the backend server.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param activityId Activity id to which the SignificantMov Measures belong
     */
    suspend fun endSignificantMovMeasure(activityId : Int)
}