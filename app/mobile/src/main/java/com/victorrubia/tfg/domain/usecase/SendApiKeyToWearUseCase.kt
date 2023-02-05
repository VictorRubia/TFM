package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.UserRepository

/**
 * Use case to send the api key to the wear device through Google's DataLayer API
 *
 * @property userRepository [UserRepository] User repository
 */
class SendApiKeyToWearUseCase(private val userRepository: UserRepository) {

    /**
     * Send the api key to the wear device
     */
    suspend fun execute() = userRepository.sendApiKeyToWear()

}