package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.step_measure.StepMeasure

/**
 * Room Data Access Object interface for the [StepMeasure] class.
 */
@Dao
interface StepMeasureDao {

    /**
     * Insert a [StepMeasure] in the database. If the [StepMeasure] already exists, replace it.
     *
     * @param stepMeasure the [StepMeasure] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStepMeasure(stepMeasures: StepMeasure)

    /**
     * Delete all [StepMeasure]s.
     */
    @Query("DELETE FROM step_measures")
    suspend fun deleteStepMeasures()

    /**
     * Select all [StepMeasure]s.
     *
     * @return a list of [StepMeasure]s.
     */
    @Query("SELECT * FROM step_measures")
    suspend fun getStepMeasures() : List<StepMeasure>
}