package com.victorrubia.tfm.domain.repository

import androidx.compose.runtime.MutableState
import com.victorrubia.tfm.data.model.step_measure.StepMeasure

/**
 * Repository interface for Step measures
 */
interface StepMeasureRepository {

    /**
     * Saves Step Measures to the repository.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param stepMeasure Step Measure to be saved
     * @param activityId Activity id to which the Step Measure belongs
     * @return True if the Step Measure was saved successfully, false otherwise
     */
    suspend fun saveStepMeasure(stepMeasure : StepMeasure, activityId : Int) : MutableState<Boolean>

    /**
     * Gets all Step Measures from the repository and sends them to the backend server.
     * Prevents losing data if the app is closed or the network connection is lost.
     *
     * @param activityId Activity id to which the Step Measures belong
     */
    suspend fun endStepMeasure(activityId : Int)
}