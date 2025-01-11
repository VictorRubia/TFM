package com.victorrubia.tfm.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victorrubia.tfm.data.model.activity_repository.ActivityRepository
import com.victorrubia.tfm.data.model.tag_repository.TagRepository
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val gson = Gson()

    /**
     * Converts a date to a string
     *
     * @param date
     * @return String with the date
     */
    @TypeConverter
    fun fromDate(date: Date): String{
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES")).format(date)
    }
    /**
     * Converts a string to a date
     *
     * @param date
     * @return Date with the string
     */
    @TypeConverter
    fun stringToDate(date: String): Date? {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES")).parse(date)

    }

    @TypeConverter
    fun activityRepositoryToString(activity: ActivityRepository): String {
        return gson.toJson(activity)
    }

    @TypeConverter
    fun stringToActivityRepository(json: String): ActivityRepository {
        val objectType = object : TypeToken<ActivityRepository>() {}.type
        return gson.fromJson(json, objectType)
    }

    @TypeConverter
    fun tagRepositoryListToString(tags: List<TagRepository>): String {
        return gson.toJson(tags)
    }

    @TypeConverter
    fun stringToTagRepositoryList(json: String): List<TagRepository> {
        val objectType = object : TypeToken<List<TagRepository>>() {}.type
        return gson.fromJson(json, objectType)
    }

}