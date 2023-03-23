package com.victorrubia.tfg.data.model.activity_repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.victorrubia.tfg.data.model.tag_repository.TagRepository
@Entity(tableName = "activity_assignation")
data class ActivityAssignation(
    @SerializedName("activity")
    val activity: ActivityRepository,
    @PrimaryKey
    @SerializedName("id")
    val id: Int, // 69
    @SerializedName("tags")
    val tags: List<TagRepository>
)