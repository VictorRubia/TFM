package com.victorrubia.tfg.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * User model
 *
 * @property apiKey API key
 */
@Entity(tableName = "current_user")
data class User (
    @PrimaryKey
    @SerializedName("api_key")
    val apiKey : String
)