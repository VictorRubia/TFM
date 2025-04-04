package com.victorrubia.tfm.data.repository.user.datasourceImpl

import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource

/**
 * Implementation of [UserCacheDataSource] interface for retrieving
 * and saving user data from/to cache
 */
class UserCacheDataSourceImpl() : UserCacheDataSource {

    // Cache for storing the user
    private var user : User? = null

    override suspend fun getUserFromCache(): User? {
        return user
    }

    override suspend fun saveUserToCache(user: User) {
        this.user = user
    }

    override suspend fun clearAll() {
        this.user = null
    }

}