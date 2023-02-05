package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.activity.Activity

/**
 * Room Data Access Object interface for the [Activity] class.
 */
@Dao
interface ActivityDao {

    /**
     * Insert a [Activity] in the database. If the activity already exists, replace it.
     *
     * @param activity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActivity(activity: Activity)

    /**
     * Delete current [Activity] from the activity table
     */
    @Query("DELETE FROM current_activity")
    suspend fun deleteActivity()

    /**
     * Get current [Activity].
     * @return Current [Activity]
     */
    @Query("SELECT * FROM current_activity")
    suspend fun getActivity() : Activity

}