package com.victorrubia.tfm.data.repository.user

import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.data.model.user.UserDetails
import com.victorrubia.tfm.domain.repository.UserRepository
import com.victorrubia.tfm.helpers.FiltersHelper

class FakeUserRepository : UserRepository {

    private var user : User? = null

    override suspend fun getUser(email: String, password: String): User? {
        if(email.isNotEmpty() && password.isNotEmpty())
            user = User("apiKey1", UserDetails("name1", email))
        return user
    }

    override suspend fun removeLocalUser() {
        user = null
    }

    override suspend fun requestPasswordReminder(email: String): Boolean {
        return FiltersHelper().validateEmail(email)
    }

    override suspend fun sendApiKeyToWear() {
        TODO("Not yet implemented")
    }

    override suspend fun isWearConnected(): Boolean? {
        TODO("Not yet implemented")
    }

}