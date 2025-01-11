package com.victorrubia.tfm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure

/**
 * Room Data Access Object interface for the [SignificantMovMeasure] class.
 */
@Dao
interface SignificantMovMeasureDao {

    /**
     * Insert a [SignificantMovMeasure] in the database. If the [SignificantMovMeasure] already exists, replace it.
     *
     * @param significantMovMeasure the [SignificantMovMeasure] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSignificantMovMeasure(significantMovMeasures: SignificantMovMeasure)

    /**
     * Delete all [SignificantMovMeasure]s.
     */
    @Query("DELETE FROM significant_mov_measures")
    suspend fun deleteSignificantMovMeasures()

    /**
     * Select all [SignificantMovMeasure]s.
     *
     * @return a list of [SignificantMovMeasure]s.
     */
    @Query("SELECT * FROM significant_mov_measures")
    suspend fun getSignificantMovMeasures() : List<SignificantMovMeasure>
}