package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfm.domain.repository.AccelerometerMeasureRepository

/**
 * Use case to save a Accelerometer measure
 *
 * @property accelerometerMeasureRespository [AccelerometerMeasureRepository]
 */
class SaveAccelerometerMeasureUseCase(private val accelerometerMeasureRespository: AccelerometerMeasureRepository) {

    /**
     * Saves a set of Accelerometer measurements
     *
     * @param accelerometerMeasure [AccelerometerMeasure]
     * @param activityId [Int] id of the activity
     */
    suspend fun execute(accelerometerMeasure : AccelerometerMeasure, activityId : Int) =
        accelerometerMeasureRespository.saveAccelerometerMeasure(accelerometerMeasure, activityId)
}