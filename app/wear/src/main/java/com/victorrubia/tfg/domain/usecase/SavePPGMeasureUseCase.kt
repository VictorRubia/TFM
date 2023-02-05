package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.domain.repository.PPGMeasureRepository

/**
 * Use case to save a PPG measure
 *
 * @property ppgMeasureRespository [PPGMeasureRepository]
 */
class SavePPGMeasureUseCase(private val ppgMeasureRespository: PPGMeasureRepository) {

    /**
     * Saves a set of PPG measurements
     *
     * @param ppgMeasure [PPGMeasure]
     * @param activityId [Int] id of the activity
     */
    suspend fun execute(ppgMeasure : PPGMeasure, activityId : Int) =
        ppgMeasureRespository.savePPGMeasure(ppgMeasure, activityId)
}