package com.victorrubia.tfm.domain.usecase

import com.victorrubia.tfm.domain.repository.UserRepository

/**
 * Use case to check if the phone is connected to the wear device
 *
 * @property userRepository [UserRepository] User repository
 */
class IsWearConnectedUseCase (private val userRepository: UserRepository) {

    /**
     * Gets the result of the execution of the use case
     */
    suspend fun execute() = userRepository.isWearConnected()

}