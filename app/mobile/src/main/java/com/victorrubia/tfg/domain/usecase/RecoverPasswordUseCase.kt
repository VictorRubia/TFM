package com.victorrubia.tfg.domain.usecase

import com.victorrubia.tfg.domain.repository.UserRepository

/**
 * Use case to request a password reminder to the user
 *
 * @property userRepository [UserRepository] User repository
 */
class RecoverPasswordUseCase(private val userRepository: UserRepository) {

    /**
     * Requests a password reminder to the user.
     *
     * @param email User email
     * @return [Boolean] True if the request was successful, false otherwise
     */
    suspend fun execute(email : String) : Boolean = userRepository.requestPasswordReminder(email)

}