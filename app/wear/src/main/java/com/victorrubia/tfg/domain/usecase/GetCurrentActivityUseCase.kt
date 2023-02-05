package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

/**
 * Use case to get the current activity
 *
 * @property activityRepository [ActivityRepository]
 */
class GetCurrentActivityUseCase(private val activityRepository : ActivityRepository) {

    /*
     * Gets current activity from the activity repository
     */
    suspend fun execute() : Activity? = activityRepository.getCurrentActivity()

}