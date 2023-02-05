package com.victorrubia.tfg.data.api

import com.victorrubia.tfg.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the Retrofit service to connect to the backend.
 */
interface TFGService {

    /**
     * Get the user information from the backend.
     *
     * @param email The user email.
     * @param password The user password.
     * @return The user information.
     */
    @GET("users/get_api_key/")
    suspend fun getUserInfo(@Query("email") email : String,
                            @Query("password_digest") password : String ) : Response<User>

    /**
     * Requests a password remember to the backend.
     *
     * @param email The user email to request the password remember.
     * @return The response of the request.
     */
    @GET("users/password_recovery/")
    suspend fun requestPasswordRecovery(@Query("email") email:String ) : Response<*>

}