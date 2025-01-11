package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.GPSMeasureRepository

/**
 * Use case to end a GPS measurement
 *
 * @property gpsMeasureRepository [GPSMeasureRepository]
 */
class EndGPSMeasureUseCase(private val gpsMeasureRespository: GPSMeasureRepository) {

    /**
     * Ends a GPS measurement
     * @param activityId Id of the activity to end the GPS measurement
     */
    suspend fun execute(activityId : Int) = gpsMeasureRespository.endGPSMeasure(activityId)
}