package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.domain.repository.UserRepository

/**
 * Use case to save a user
 *
 * @property userRepository [UserRepository]
 */
class SaveUserUseCase(private val userRepository: UserRepository) {

    /**
     * Saves a user to the repository
     *
     * @param user [User] user to save
     */
    suspend fun execute(user : User) = userRepository.saveUser(user)

}