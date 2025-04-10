package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.domain.repository.GPSMeasureRepository

/**
 * Use case to save a GPS measure
 *
 * @property gpsMeasureRespository [GPSMeasureRepository]
 */
class SaveGPSMeasureUseCase(private val gpsMeasureRespository: GPSMeasureRepository) {

    /**
     * Saves a set of GPS measurements
     *
     * @param gpsMeasure [GPSMeasure]
     * @param activityId [Int] id of the activity
     */
    suspend fun execute(gpsMeasure : GPSMeasure, activityId : Int) =
        gpsMeasureRespository.saveGPSMeasure(gpsMeasure, activityId)
}