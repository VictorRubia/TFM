package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfm.domain.repository.ActivityAssignationRepository


class GetActivitiesAssignedUseCase(private val activityAssignationRepository : ActivityAssignationRepository) {

    suspend fun execute() : List<ActivityAssignation> = activityAssignationRepository.getActivitiesAssigned()

}