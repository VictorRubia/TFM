package com.victorrubia.tfg.data.repository.tag

import android.util.Log
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.repository.tag.datasource.TagCacheDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.domain.repository.TagRepository
import kotlinx.coroutines.delay
import java.util.*

/**
 * Implementation of the [TagRepository] interface that combines [TagRemoteDataSource] and
 * [TagLocalDataSource] to implement the required [TagRepository] methods.
 *
 * @property tagRemoteDataSource the remote data source that will be used to fetch data.
 * @property tagLocalDataSource the local data source that will be used to store data.
 * @property tagCacheDataSource the cache data source that will be used to store data.
 */
class TagRepositoryImpl(
    private val tagRemoteDataSource : TagRemoteDataSource,
    private val tagLocalDataSource: TagLocalDataSource,
    private val tagCacheDataSource: TagCacheDataSource
) : TagRepository {


    override suspend fun addTag(tag: String, datetime: Date, activityId: Int): Tag {
        return addTagToCache(tag, datetime, activityId)
    }

    /**
     * Sends a request to the remote data source to add a tag to the activity.
     *
     * @param tag the tag to be added.
     * @param datetime the datetime of the tag.
     * @param activityId the id of the activity.
     * @return the tag that was added.
     */
    suspend fun addTagToAPI(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            val response = tagRemoteDataSource.addTag(tag, datetime, activityId)
            if(response.body() != null){
                tagLocalDataSource.clearAll()
                tagCacheDataSource.clearAll()
            }
        }
        catch (exception : Exception){
            delay(15000)
            val tagsPendientes = tagLocalDataSource.getTagsFromDB()
            if(tagsPendientes.isNotEmpty()){
                Log.d("MyTag", "Mando tags porq tengo pendientes")
                tagsPendientes.forEach{
                    try {
                        tagRemoteDataSource.addTag(it.tag, it.datetime, it.activityId)
                    }
                    catch (exception : Exception){
                        Log.e("MyTag", exception.message.toString())
                    }
                }
            }
            Log.e("MyTag", exception.message.toString())
        }

        return Tag(tag, datetime, activityId)
    }

    /**
     * Sends a request to the local data source to add a tag to the activity.
     *
     * @param tag the tag to be added.
     * @param datetime the datetime of the tag.
     * @param activityId the id of the activity.
     * @return the tag that was added.
     */
    suspend fun addTagToDB(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            tagLocalDataSource.saveTagToDB(Tag(tag, datetime, activityId))
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return addTagToAPI(tag, datetime, activityId)
    }

    /**
     * Sends a request to the cache data source to add a tag to the activity.
     *
     * @param tag the tag to be added.
     * @param datetime the datetime of the tag.
     * @param activityId the id of the activity.
     * @return the tag that was added.
     */
    suspend fun addTagToCache(tag: String, datetime: Date, activityId: Int) : Tag{
        try {
            tagCacheDataSource.saveTagToCache(Tag(tag, datetime, activityId))
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }

        return addTagToDB(tag, datetime, activityId)

    }
}