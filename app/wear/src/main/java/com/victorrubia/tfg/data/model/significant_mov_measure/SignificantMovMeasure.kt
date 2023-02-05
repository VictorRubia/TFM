package com.victorrubia.tfg.data.model.significant_mov_measure

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
@Entity(tableName = "significant_mov_measures")
@Serializable
data class SignificantMovMeasure(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val trigger: Int,
    @TypeConverters(Converters::class)
    @Serializable(with = DateSerializer::class)
    val date: Date,
)