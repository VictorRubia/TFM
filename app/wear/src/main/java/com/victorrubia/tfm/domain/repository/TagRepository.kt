package com.victorrubia.tfm.domain.repository

import com.victorrubia.tfm.data.model.tag.Tag
import java.util.*

/**
 * Tag repository interface
 */
interface TagRepository {

    /**
     * Adds a new tag to the repository
     *
     * @param tag Tag to add
     * @param datetime Date of the tag
     * @param activityId Activity id
     * @return Added tag
     */
    suspend fun addTag(tag : String, datetime : Date, activityId : Int) : Tag
}