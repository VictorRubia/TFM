package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

/**
 * Use case to create a new activity
 *
 * @property activityRepository [ActivityRepository]
 */
class NewActivityUseCase(private val activityRepository : ActivityRepository) {

    /**
     * Creates a new activity in the backend server.
     *
     * @param name Name of the activity
     * @param startTimestamp Start timestamp of the activity
     */
    suspend fun execute(name: String, startTimestamp: String) : Activity = activityRepository.newActivity(name, startTimestamp)

}