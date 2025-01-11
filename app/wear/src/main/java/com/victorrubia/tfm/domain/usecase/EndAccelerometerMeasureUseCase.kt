package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.AccelerometerMeasureRepository

/**
 * Use case to end a Accelerometer measurement
 *
 * @property accelerometerMeasureRepository [AccelerometerMeasureRepository]
 */
class EndAccelerometerMeasureUseCase(private val accelerometerMeasureRespository: AccelerometerMeasureRepository) {

    /**
     * Ends a Accelerometer measurement
     * @param activityId Id of the activity to end the Accelerometer measurement
     */
    suspend fun execute(activityId : Int) = accelerometerMeasureRespository.endAccelerometerMeasure(activityId)
}