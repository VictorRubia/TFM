package com.victorrubia.tfg.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victorrubia.tfg.data.model.user.UserDetails

/**
 * Converters for the UserDetails class
 */
class Converters {

    /**
     * Convert a UserDetails Object to a Json
     */
    @TypeConverter
    fun fromUserDetails(user: UserDetails?): String? {
        return if(user == null) null else Gson().toJson(user)
    }

    /**
     * Convert a String to a UserDetails Object
     */
    @TypeConverter
    fun stringToUserDetails(jsonUser: String?): UserDetails? {
        val notesType = object : TypeToken<UserDetails>() {}.type
        return Gson().fromJson<UserDetails>(jsonUser, notesType)
    }
}