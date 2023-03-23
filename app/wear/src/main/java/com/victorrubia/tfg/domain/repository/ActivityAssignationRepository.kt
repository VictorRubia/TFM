package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation

/**
 * Activity repository interface
 */
interface ActivityAssignationRepository {

    suspend fun getActivitiesAssigned() : List<ActivityAssignation>

    suspend fun clearActivitiesAssigned()

}