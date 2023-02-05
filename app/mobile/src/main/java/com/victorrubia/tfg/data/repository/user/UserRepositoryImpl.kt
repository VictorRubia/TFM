package com.victorrubia.tfg.data.repository.user

import android.util.Log
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfg.domain.repository.UserRepository
import retrofit2.Response

/**
 * Implementation of the [UserRepository] interface that combines [UserLocalDataSource]
 * and [UserRemoteDataSource] to implement the data layer.
 * @property userRemoteDataSource the remote data source that accesses the network.
 * @property userLocalDataSource the local data source that accesses the database.
 * @property userCacheDataSource the cache data source that accesses the cache.
 */
class UserRepositoryImpl(
    private val userRemoteDataSource : UserRemoteDatasource,
    private val userLocalDataSource : UserLocalDataSource,
    private val userCacheDataSource : UserCacheDataSource,
) : UserRepository {

    override suspend fun getUser(email : String, password : String): User? {
        return getUserFromCache(email, password)
    }

    override suspend fun removeLocalUser() {
        userCacheDataSource.clearAll()
        userLocalDataSource.clearAll()
    }

    override suspend fun requestPasswordReminder(email : String): Boolean {
        return requestPasswordReminderToAPI(email)
    }

    override suspend fun sendApiKeyToWear() {
        try{
            userCacheDataSource.getUserFromCache()?.apiKey?.let { userRemoteDataSource.sendApiKeyToWear(it) }
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }
    }

    override suspend fun isWearConnected(): Boolean? {
        return userRemoteDataSource.isWearConnected()
    }

    /**
     * Get the user from the API backend based on the email and password.
     *
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user if credentials are correct, null otherwise.
     */
    private suspend fun getUserFromAPI(email : String, password : String) : User?{
        var user : User? = null

        if(email.isNotEmpty()){
            try{
                val response = userRemoteDataSource.getUser(email, password)
                val body = response.body()
                if(body != null){
                    user = User(body.apiKey, body.userDetails)
                }
            }
            catch (exception : Exception){
                Log.i("MyTag", exception.message.toString())
            }
        }

        return user
    }

    /**
     * Tries to get the User information from the local data source. If this fails, it tries to get it
     * from the remote data source.
     *
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the [User] information
     */
    private suspend fun getUserFromDB(email : String, password : String) : User?{
        var user : User? = null

        try{
            user = userLocalDataSource.getUserFromDB()
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(user != null){
            return user
        }
        else{
            user = getUserFromAPI(email, password)
            if(user != null) userLocalDataSource.saveUserToDB(user)
        }

        return user
    }

    /**
     * Tries to get the User information from the cache data source. If this fails, it tries to get it
     * from the local data source.
     *
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the [User] information
     */
    private suspend fun getUserFromCache(email : String, password : String) : User?{
        var user : User? = null

        try{
            user = userCacheDataSource.getUserFromCache()
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(user != null){
            return user
        }
        else{
            user = getUserFromDB(email, password)
            if (user != null) userCacheDataSource.saveUserToCache(user)
        }

        return user
    }

    /**
     * Requests a password reminder to the API backend.
     *
     * @param email the email of the user.
     * @return true if the request was successful, false otherwise.
     */
    private suspend fun requestPasswordReminderToAPI(email : String) : Boolean{

        var confirmation : Response<*>? = null

        try{
            confirmation = userRemoteDataSource.rememberPassword(email)
            Log.i("MYTAG", "Request Password -> " + (confirmation.raw().code == 200).toString())
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(confirmation != null){
            return confirmation.raw().code == 200
        }

        return false

    }

}