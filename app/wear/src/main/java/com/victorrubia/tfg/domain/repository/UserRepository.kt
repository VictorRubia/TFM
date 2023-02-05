package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.user.User

/**
 * Repository interface for the user
 */
interface UserRepository {

    /**
     * Get the user from the repositories.
     */
    suspend fun requestUser()

    /**
     * Get the user from the local data sources.
     *
     * @return the current user
     */
    suspend fun getUser() : User?

    /**
     * Saves the received user in the local data sources.
     *
     * @param user the user to be saved
     */
    suspend fun saveUser(user : User)
}