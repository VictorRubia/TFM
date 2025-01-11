package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.step_measure.StepMeasure
import com.victorrubia.tfm.domain.repository.StepMeasureRepository

/**
 * Use case to save a Step measure
 *
 * @property stepMeasureRespository [StepMeasureRepository]
 */
class SaveStepMeasureUseCase(private val stepMeasureRespository: StepMeasureRepository) {

    /**
     * Saves a set of Step measurements
     *
     * @param stepMeasure [StepMeasure]
     * @param activityId [Int] id of the activity
     */
    suspend fun execute(stepMeasure : StepMeasure, activityId : Int) =
        stepMeasureRespository.saveStepMeasure(stepMeasure, activityId)
}