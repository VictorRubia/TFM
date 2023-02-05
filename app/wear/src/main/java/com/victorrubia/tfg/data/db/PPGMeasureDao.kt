package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure

/**
 * Room Data Access Object interface for the [PPGMeasure] class.
 */
@Dao
interface PPGMeasureDao {

    /**
     * Insert a [PPGMeasure] in the database. If the [PPGMeasure] already exists, replace it.
     *
     * @param ppgMeasure the [PPGMeasure] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePPGMeasure(ppgMeasures: PPGMeasure)

    /**
     * Delete all [PPGMeasure]s.
     */
    @Query("DELETE FROM ppg_measures")
    suspend fun deletePPGMeasures()

    /**
     * Select all [PPGMeasure]s.
     *
     * @return a list of [PPGMeasure]s.
     */
    @Query("SELECT * FROM ppg_measures")
    suspend fun getPPGMeasures() : List<PPGMeasure>
}