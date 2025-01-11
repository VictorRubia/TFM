package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.ActivityAssignationRepository

class ClearActivitiesAssignedUseCase (private val activityAssignationRepository : ActivityAssignationRepository) {

    suspend fun execute() = activityAssignationRepository.clearActivitiesAssigned()

}
