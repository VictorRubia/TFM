package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.domain.repository.TagRepository
import java.util.*

/**
 * Use case to add a tag to an activity in progress.
 *
 * @property tagRepository [TagRepository] Tag repository.
 */
class AddTagUseCase(private val tagRepository: TagRepository) {

    /**
     * Adds a tag to an activity in progress.
     *
     * @param tag JSON Tags to add.
     * @param datetime timestamp of the tag.
     * @param activityId Activity id.
     * @return Tag object.
     */
    suspend fun execute(tag: String, datetime: Date, activityId: Int) : Tag = tagRepository.addTag(tag, datetime, activityId)

}