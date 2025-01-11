package com.victorrubia.tfm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure

/**
 * Room Data Access Object interface for the [GPSMeasure] class.
 */
@Dao
interface GPSMeasureDao {

    /**
     * Insert a [GPSMeasure] in the database. If the [GPSMeasure] already exists, replace it.
     *
     * @param gpsMeasure the [GPSMeasure] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGPSMeasure(gpsMeasures: GPSMeasure)

    /**
     * Delete all [GPSMeasure]s.
     */
    @Query("DELETE FROM gps_measures")
    suspend fun deleteGPSMeasures()

    /**
     * Select all [GPSMeasure]s.
     *
     * @return a list of [GPSMeasure]s.
     */
    @Query("SELECT * FROM gps_measures")
    suspend fun getGPSMeasures() : List<GPSMeasure>
}