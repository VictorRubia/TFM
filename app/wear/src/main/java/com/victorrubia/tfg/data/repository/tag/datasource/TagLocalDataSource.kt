package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag

/**
 * Interface for the local data source of the tags
 */
interface TagLocalDataSource {
    /**
     * Gets all the tags from the local data source
     *
     * @return the list of tags
     */
    suspend fun getTagsFromDB() : List<Tag>

    /**
     * Saves the tags in the local data source
     *
     * @param tag the tag to save
     */
    suspend fun saveTagToDB(tag : Tag)

    /**
     * Deletes all the tags in the local data source
     */
    suspend fun clearAll()
}