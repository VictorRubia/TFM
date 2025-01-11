package com.victorrubia.tfm.domain.repository

import com.victorrubia.tfm.data.model.activity_repository.ActivityAssignation

/**
 * Activity repository interface
 */
interface ActivityAssignationRepository {

    suspend fun getActivitiesAssigned() : List<ActivityAssignation>

    suspend fun clearActivitiesAssigned()

}