package com.victorrubia.tfg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorrubia.tfg.data.model.user.User

/**
 * Room Data Access Object for the [User] class.
 */
@Dao
interface UserDao {

    /**
     * Save a [User] in the database. If the user already exists, replace it.
     *
     * @param user the user to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    /**
     * Delete current user from the [User] table.
     */
    @Query("DELETE FROM current_user")
    suspend fun deleteUser()

    /**
     * Select current user from the [User] table.
     *
     * @return current user.
     */
    @Query("SELECT * FROM current_user")
    suspend fun getUser() : User

}