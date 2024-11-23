package com.victorrubia.tfg.data.model.step_measure

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
@Entity(tableName = "step_measures")
@Serializable
data class StepMeasure(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val count: Int,
    val timestamp: Long,
)