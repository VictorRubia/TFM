package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.domain.repository.ActivityRepository

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
    suspend fun execute(activityId: Int, startTimestamp: String) : Activity = activityRepository.newActivity(activityId, startTimestamp)

}