package com.victorrubia.tfg.data.repository.activity_assignation.datasourceImpl

import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import retrofit2.Response

class ActivityAssignationRemoteDataSourceImpl(
    private val tfgService: TFGService,
    private val user : UserCacheDataSource
) : ActivityAssignationRemoteDataSource {

    override suspend fun getActivitiesAssigned() : Response<List<ActivityAssignation>> =
        tfgService.getAssignedActivities("Bearer ${user.getUserFromCache()!!.apiKey}")

}