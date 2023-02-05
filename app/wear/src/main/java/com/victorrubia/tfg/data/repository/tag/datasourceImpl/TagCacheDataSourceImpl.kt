package com.victorrubia.tfg.data.repository.tag.datasourceImpl

import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.repository.tag.datasource.TagCacheDataSource

/**
 * Implementation of [TagCacheDataSource] interface for retrieving and saving data from and to cache data source.
 */
class TagCacheDataSourceImpl : TagCacheDataSource {

    /**
     * Cache of tags.
     */
    private var tags : MutableList<Tag> = ArrayList()

    override suspend fun getTagsFromCache(): List<Tag> {
        return tags
    }

    override suspend fun saveTagToCache(tag: Tag) {
        tags.add(tag)
    }

    override suspend fun clearAll() {
        tags.clear()
    }

}