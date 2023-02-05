package com.victorrubia.tfg.data.model.ppg_measure


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

/**
 * Object to serialize and deserialize Date objects when storing [PPGMeasure] in RoomDB with KSerializer
 */
object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES")).parse(decoder.decodeString())!!
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES"))
        encoder.encodeString(df.format(value))
    }
}

/**
 * Class that represents a PPG measure
 *
 * @property id The id of the measure
 * @property timestamp The timestamp of the measure
 * @property value The value of the measure
 */
@Entity(tableName = "ppg_measures")
@Serializable
data class PPGMeasure(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val measure: Int,
    @TypeConverters(Converters::class)
    @Serializable(with = DateSerializer::class)
    val date: Date,
)