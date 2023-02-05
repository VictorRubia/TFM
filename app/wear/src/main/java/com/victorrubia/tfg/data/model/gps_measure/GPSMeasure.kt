package com.victorrubia.tfg.data.model.gps_measure

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.ppg_measure.DateSerializer
import kotlinx.serialization.*
import java.util.*

/**
 * Class that represents a PPG measure
 *
 * @property id The id of the measure
 * @property timestamp The timestamp of the measure
 * @property value The value of the measure
 */
@Entity(tableName = "gps_measures")
@Serializable
data class GPSMeasure(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val latitude: Double,
    val longitude: Double,
    @TypeConverters(Converters::class)
    @Serializable(with = DateSerializer::class)
    val date: Date,
)