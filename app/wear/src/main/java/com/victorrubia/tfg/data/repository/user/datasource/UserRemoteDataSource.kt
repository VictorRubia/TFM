package com.victorrubia.tfg.data.repository.user.datasource

/**
 * Interface for the remote data source of the user repository.
 */
interface UserRemoteDataSource {

    /**
     * Gets the user data from the mobile phone paired with the watch.
     * In order to achieve this, the user must have previously logged in
     * the mobile phone companion app. Thus, wear will send a message to
     * the mobile phone to request the user data through the DataLayer API.
     * Wear will be waiting for a response from the mobile phone with the user data.
     */
    suspend fun requestUser()

}