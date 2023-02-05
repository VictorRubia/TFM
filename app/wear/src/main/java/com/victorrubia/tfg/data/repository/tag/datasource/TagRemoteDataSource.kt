package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag
import retrofit2.Response
import java.util.*

/**
 * Interface that defines the contract between the remote data source and the rest of the system.
 */
interface TagRemoteDataSource {

    /**
     * Adds a new tag to the activity in the remote data source.
     *
     * @param tag The tag to be added.
     * @param datetime The datetime of the tag.
     * @param activityId The id of the activity.
     * @return The added tag.
     */
    suspend fun addTag(tag : String, datetime : Date, activityId : Int) : Response<Tag>
}