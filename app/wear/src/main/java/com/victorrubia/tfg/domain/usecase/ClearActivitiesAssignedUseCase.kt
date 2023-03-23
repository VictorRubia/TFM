package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.ActivityAssignationRepository

class ClearActivitiesAssignedUseCase (private val activityAssignationRepository : ActivityAssignationRepository) {

    suspend fun execute() = activityAssignationRepository.clearActivitiesAssigned()

}
