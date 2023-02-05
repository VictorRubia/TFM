package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.PPGMeasureRepository

/**
 * Use case to end a PPG measurement
 *
 * @property ppgMeasureRepository [PPGMeasureRepository]
 */
class EndPPGMeasureUseCase(private val ppgMeasureRespository: PPGMeasureRepository) {

    /**
     * Ends a PPG measurement
     * @param activityId Id of the activity to end the PPG measurement
     */
    suspend fun execute(activityId : Int) = ppgMeasureRespository.endPPGMeasure(activityId)
}