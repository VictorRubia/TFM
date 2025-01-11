package com.victorrubia.tfm.data.repository.activity_assignation.datasource

import com.victorrubia.tfm.data.model.activity_repository.ActivityAssignation
import retrofit2.Response


interface ActivityAssignationRemoteDataSource {

    suspend fun getActivitiesAssigned() : Response<List<ActivityAssignation>>

    suspend fun downloadImage(activities : List<ActivityAssignation>)

    suspend fun clearAllImages()

}