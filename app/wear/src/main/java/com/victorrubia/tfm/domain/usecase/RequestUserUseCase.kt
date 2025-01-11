package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.UserRepository

/**
 * Use case to request a user
 *
 * @property userRepository [UserRepository] User repository
 */
class RequestUserUseCase(private val userRepository: UserRepository) {

    /**
     * Request the current logged in user to [UserRepository].
     */
    suspend fun execute() = userRepository.requestUser()
}