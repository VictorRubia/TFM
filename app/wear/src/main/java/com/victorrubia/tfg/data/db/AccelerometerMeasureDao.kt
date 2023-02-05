package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.accelerometer_measure.AccelerometerMeasure

/**
 * Room Data Access Object interface for the [AccelerometerMeasure] class.
 */
@Dao
interface AccelerometerMeasureDao {

    /**
     * Insert a [AccelerometerMeasure] in the database. If the [AccelerometerMeasure] already exists, replace it.
     *
     * @param accelerometerMeasure the [AccelerometerMeasure] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccelerometerMeasure(accelerometerMeasures: AccelerometerMeasure)

    /**
     * Delete all [AccelerometerMeasure]s.
     */
    @Query("DELETE FROM accelerometer_measures")
    suspend fun deleteAccelerometerMeasures()

    /**
     * Select all [AccelerometerMeasure]s.
     *
     * @return a list of [AccelerometerMeasure]s.
     */
    @Query("SELECT * FROM accelerometer_measures")
    suspend fun getAccelerometerMeasures() : List<AccelerometerMeasure>
}