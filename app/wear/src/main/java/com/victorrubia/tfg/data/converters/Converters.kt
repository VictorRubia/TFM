package com.victorrubia.tfg.data.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

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

}