package com.victorrubia.tfg.data.repository.user.datasource

import com.victorrubia.tfg.data.model.user.User
import retrofit2.Response

/**
 * Interface for the remote data source of the user repository
 */
interface UserRemoteDatasource {

    /**
     * Gets the user from the remote data source
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return the user
     */
    suspend fun getUser(email : String, password : String): Response<User>

    /**
     * Requests a password remember to the remote data source
     *
     * @param email the email of the user to remember the password
     * @return the response of the request
     */
    suspend fun rememberPassword(email : String): Response<*>

    /**
     * Sends the user API Key to WearOS through DataLayer API.
     *
     * @param apiKey the API Key of the user
     */
    suspend fun sendApiKeyToWear(apiKey : String)

    /**
     * Computes if a wear device is connected to the phone
     *
     * @return true if the device is connected, false otherwise
     */
    suspend fun isWearConnected() : Boolean?

}