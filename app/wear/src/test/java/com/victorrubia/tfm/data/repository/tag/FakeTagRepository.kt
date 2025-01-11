package com.victorrubia.tfm.data.repository.tag

import com.victorrubia.tfm.data.model.tag.Tag
import com.victorrubia.tfm.domain.repository.TagRepository
import java.util.*

class FakeTagRepository : TagRepository {

    private var tags = mutableListOf<Tag>()

    override suspend fun addTag(tag: String, datetime: Date, activityId: Int): Tag {
        tags.add(Tag(tag, datetime, activityId))
        return Tag(tag, datetime, activityId)
    }


}