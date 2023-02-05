package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfg.domain.repository.SignificantMovMeasureRepository

/**
 * Use case to save a SignificantMov measure
 *
 * @property significantMovMeasureRespository [SignificantMovMeasureRepository]
 */
class SaveSignificantMovMeasureUseCase(private val significantMovMeasureRespository: SignificantMovMeasureRepository) {

    /**
     * Saves a set of SignificantMov measurements
     *
     * @param significantMovMeasure [SignificantMovMeasure]
     * @param activityId [Int] id of the activity
     */
    suspend fun execute(significantMovMeasure : SignificantMovMeasure, activityId : Int) =
        significantMovMeasureRespository.saveSignificantMovMeasure(significantMovMeasure, activityId)
}