package com.victorrubia.tfg.data.repository.tag.datasource

import com.victorrubia.tfg.data.model.tag.Tag

/**
 * Interface for the cache data source of the tags repository.
 */
interface TagCacheDataSource {
    /**
     * Gets the cached tags.
     *
     * @return the cached tags or null if there are no cached tags.
     */
    suspend fun getTagsFromCache() : List<Tag>

    /**
     * Saves the given tags to the cache.
     *
     * @param tag the tags to be saved.
     */
    suspend fun saveTagToCache(tag : Tag)

    /**
     * Clears the cache from all the tags.
     */
    suspend fun clearAll()
}