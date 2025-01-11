package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.domain.repository.UserRepository

/**
 * Use case to get a user from the repository
 *
 * @property userRepository [UserRepository] User repository
 */
class GetUserUseCase(private val userRepository: UserRepository) {

    /**
     * Gets the current user from the repository
     *
     * @return [User] User object. Null if not logged in.
     */
    suspend fun execute(): User? = userRepository.getUser()

}