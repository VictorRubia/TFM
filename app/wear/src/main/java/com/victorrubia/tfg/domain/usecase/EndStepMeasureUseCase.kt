package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.StepMeasureRepository

/**
 * Use case to end a Step measurement
 *
 * @property stepMeasureRepository [StepMeasureRepository]
 */
class EndStepMeasureUseCase(private val stepMeasureRespository: StepMeasureRepository) {

    /**
     * Ends a Step measurement
     * @param activityId Id of the activity to end the Step measurement
     */
    suspend fun execute(activityId : Int) = stepMeasureRespository.endStepMeasure(activityId)
}