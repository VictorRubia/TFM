package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.tag.Tag

/**
 * Room Data Access Object interface for the [Tag] class.
 */
@Dao
interface TagDao {

    /**
     * Insert a [Tag] in the database. If the [Tag] already exists, replace it.
     *
     * @param tag the [Tag] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTag(tag: Tag)

    /**
     * Delete all [Tag]s.
     */
    @Query("DELETE FROM current_tags")
    suspend fun deleteTags()

    /**
     * Get all [Tag]s.
     *
     * @return a list of [Tag]s.
     */
    @Query("SELECT * FROM current_tags")
    suspend fun getTags() : List<Tag>

}