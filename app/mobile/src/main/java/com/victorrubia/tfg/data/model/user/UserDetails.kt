package com.victorrubia.tfg.data.model.user

import com.google.gson.annotations.SerializedName

/**
 * User details model
 *
 * @property name User name
 * @property email User email
 */
data class UserDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
)