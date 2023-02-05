package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User

/**
 * Interface for the local data source of the user repository.
 */
interface UserLocalDataSource {
    /**
     * Gets the user from the local data source.
     *
     * @return the user from the local data source.
     */
    suspend fun getUserFromDB() : User

    /**
     * Saves the user in the local data source.
     *
     * @param user the user to be saved.
     */
    suspend fun saveUserToDB(user : User)

    /**
     * Deletes the user from the local data source.
     */
    suspend fun clearAll()
}