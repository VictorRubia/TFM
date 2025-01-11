package com.victorrubia.tfm.data.model.accelerometer_measure

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Class that represents a Accelerometer measure
 *
 * @property id The id of the measure
 * @property timestamp The timestamp of the measure
 * @property value The value of the measure
 */
@Entity(tableName = "accelerometer_measures")
@Serializable
data class AccelerometerMeasure(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val ejeX: Float,
    val ejeY: Float,
    val ejeZ: Float,
    val timestamp: Long,
)