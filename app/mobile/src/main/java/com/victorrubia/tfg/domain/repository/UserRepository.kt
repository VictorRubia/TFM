package com.victorrubia.tfg.domain.repository

import com.victorrubia.tfg.data.model.user.User

/**
 * Repository interface for the user
 */
interface UserRepository {

    /**
     * Get the user from the repository
     *
     * @param email the user email
     * @param password the user password
     * @return the user
     */
    suspend fun getUser(email : String = "", password : String = "") : User?

    /**
     * Removes the user from local repositories when the user is logged out
     */
    suspend fun removeLocalUser()

    /**
     * Requests a password reminder to the backend API for the given email
     *
     * @param email the user email
     * @return true if the request was successful, false otherwise
     */
    suspend fun requestPasswordReminder(email : String) : Boolean

    /**
     * Sends the user data to the watch paired with this device.
     * In order to achieve this, the user must be logged in.
     * Thus, the device will send a message to the watch through the DataLayer API.
     */
    suspend fun sendApiKeyToWear()

    /**
     * Checks if a wearOS device is connected and paired to the phone
     *
     * @return true if the device is connected and paired, false otherwise
     */
    suspend fun isWearConnected() : Boolean?

}