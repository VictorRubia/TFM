package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

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