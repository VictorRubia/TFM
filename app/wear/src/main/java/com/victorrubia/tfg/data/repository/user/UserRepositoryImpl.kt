package com.victorrubia.tfg.data.repository.user

import android.util.Log
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.domain.repository.UserRepository

/**
 * Implementation of the [UserRepository] interface that works with data sources.
 *
 * @property userRemoteDataSource the remote data source
 * @property userLocalDataSource the local data source
 * @property userCacheDataSource the cache data source
 */
class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userCacheDataSource: UserCacheDataSource,
) : UserRepository {


    override suspend fun requestUser(){
        requestUserToPhone()
    }


    override suspend fun getUser(): User? {
        return getUserFromCache()
    }


    override suspend fun saveUser(user: User) {
        saveUserToCache(user)
    }

    /**
     * Tries to get the User information from the cache. If this fails, it tries to get it from the
     * local data source.
     *
     * @return the [User] information
     */
    suspend fun getUserFromCache() : User?{
        var user : User? = null

        try {
            user = userCacheDataSource.getUserFromCache()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        if(user != null){
            return user
        }
        else{
            user = getUserFromDB()
            if(user != null) userCacheDataSource.saveUserToCache(user)
        }

        return user
    }

    /**
     * Tries to get the User information from the local data source. If this fails, it tries to get it
     * from the remote data source.
     *
     * @return the [User] information
     */
    suspend fun getUserFromDB() : User?{
        var user : User? = null

        try {
            user = userLocalDataSource.getUserFromDB()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        if(user != null){
            return user
        }
        else{
            requestUserToPhone()
            return null
        }
    }

    /**
     * Requests the [User] info to the companion app installed on a linked phone.
     */
    suspend fun requestUserToPhone(){
        userRemoteDataSource.requestUser()
    }

    /**
     * Saves the [User] information to the local data source.
     *
     * @param user the [User] information
     */
    suspend fun saveUserToDB(user : User){
        try {
            userLocalDataSource.clearAll()
            userLocalDataSource.saveUserToDB(user)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    /**
     * Saves the [User] information to the cache.
     *
     * @param user the [User] information
     */
    suspend fun saveUserToCache(user : User){
        try {
            userCacheDataSource.clearAll()
            userCacheDataSource.saveUserToCache(user)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        saveUserToDB(user)
    }
}