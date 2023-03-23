package com.victorrubia.tfg.data.repository.activity_assignation.datasource

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import retrofit2.Response


interface ActivityAssignationRemoteDataSource {

    suspend fun getActivitiesAssigned() : Response<List<ActivityAssignation>>

}