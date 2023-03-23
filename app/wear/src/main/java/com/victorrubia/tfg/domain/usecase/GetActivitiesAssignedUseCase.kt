package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.domain.repository.ActivityAssignationRepository


class GetActivitiesAssignedUseCase(private val activityAssignationRepository : ActivityAssignationRepository) {

    suspend fun execute() : List<ActivityAssignation> = activityAssignationRepository.getActivitiesAssigned()

}