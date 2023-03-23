package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation

/**
 * Room Data Access Object interface for the [Activity] class.
 */
@Dao
interface ActivityAssignationDao {

    /**
     * Insert a [Activity] in the database. If the activity already exists, replace it.
     *
     * @param activity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActivityAssignation(activity: ActivityAssignation)

    /**
     * Delete current [Activity] from the activity table
     */
    @Query("DELETE FROM activity_assignation")
    suspend fun deleteActivityAssignation()

    /**
     * Get current [Activity].
     * @return Current [Activity]
     */
    @Query("SELECT * FROM activity_assignation")
    suspend fun getActivityAssignation() : List<ActivityAssignation>

}