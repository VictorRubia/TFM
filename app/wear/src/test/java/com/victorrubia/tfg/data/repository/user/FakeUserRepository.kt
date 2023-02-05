package com.victorrubia.tfg.data.repository.user

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

class FakeUserRepository(private var user : User? = null): UserRepository {

    override suspend fun requestUser() {
        user = User("apiKey1")
    }

    override suspend fun getUser(): User? {
        return user
    }

    override suspend fun saveUser(user: User) {
        this.user = user
    }


}