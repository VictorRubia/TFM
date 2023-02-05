package com.victorrubia.tfg.data.model.accelerometer_measure

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.ppg_measure.DateSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
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
    @TypeConverters(Converters::class)
    @Serializable(with = DateSerializer::class)
    val date: Date,
)