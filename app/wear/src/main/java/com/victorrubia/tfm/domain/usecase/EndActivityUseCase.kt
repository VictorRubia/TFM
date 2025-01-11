package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.domain.repository.ActivityRepository

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