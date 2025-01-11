package com.victorrubia.tfm.data.model.significant_mov_measure

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val timestamp: Long,
)