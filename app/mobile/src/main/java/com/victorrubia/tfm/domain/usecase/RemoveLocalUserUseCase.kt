package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.UserRepository

/**
 * Use case to remove user from local respositories
 *
 * @property userRepository [UserRepository] User repository
 */
class RemoveLocalUserUseCase(private val userRepository: UserRepository) {

    /**
     * Executes the use case
     */
    suspend fun execute() = userRepository.removeLocalUser()

}