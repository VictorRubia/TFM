package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.SignificantMovMeasureRepository

/**
 * Use case to end a SignificantMov measurement
 *
 * @property significantMovMeasureRepository [SignificantMovMeasureRepository]
 */
class EndSignificantMovMeasureUseCase(private val significantMovMeasureRespository: SignificantMovMeasureRepository) {

    /**
     * Ends a SignificantMov measurement
     * @param activityId Id of the activity to end the SignificantMov measurement
     */
    suspend fun execute(activityId : Int) = significantMovMeasureRespository.endSignificantMovMeasure(activityId)
}