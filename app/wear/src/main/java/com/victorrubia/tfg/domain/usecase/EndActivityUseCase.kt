package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

/**
 * Use case to end an activity
 * @property activityRepository [ActivityRepository] repository to access to the activity
 */
class EndActivityUseCase(private val activityRepository : ActivityRepository) {

    /**
     * Ends current activity
     * @return the ended activity
     */
    suspend fun execute() : Activity? = activityRepository.endActivity()

}