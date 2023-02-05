package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.repository.UserRepository

/**
 * Use case to get a user from the repository
 *
 * @property userRepository [UserRepository] User repository
 */
class GetUserUseCase(private val userRepository: UserRepository) {

    /**
     * Get a user from the repository
     *
     * @param email User email
     * @param password User password
     * @return [User] User
     */
    suspend fun execute(email : String, password : String) : User? = userRepository.getUser(email, password)

}