package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User

/**
 * Interface for the cache data source of the user repository
 */
interface UserCacheDataSource {
    /**
     * Gets the user from the cache
     * @return the user from the cache
     */
    suspend fun getUserFromCache() : User?
    /**
     * Saves the user in the cache
     * @param user the user to save
     */
    suspend fun saveUserToCache(user : User)
    /**
     * Clears the cache of the current user
     */
    suspend fun clearAll()
}