package com.victorrubia.tfg.data.model.user
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.victorrubia.tfg.data.converters.Converters

/**
 * User model
 *
 * @property apiKey API key
 * @property userDetails User details
 */
@Entity(tableName = "current_user")
data class User(
    @PrimaryKey
    @SerializedName("api_key")
    val apiKey: String,
    @TypeConverters(Converters::class)
    @SerializedName("user_details")
    val userDetails: UserDetails
)