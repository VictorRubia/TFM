package com.victorrubia.tfg.presentation.logged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase

/**
 * Factory for creating a [LoggedViewModel]
 *
 * @property getUserUseCase [GetUserUseCase] to get the user
 * @property removeLocalUserUseCase [RemoveLocalUserUseCase] to remove the user
 * @property sendApiKeyToWearUseCase [SendApiKeyToWearUseCase] to send the api key to the wear
 * @property isWearConnectedUseCase [IsWearConnectedUseCase] to know if the wear is connected
 */
class LoggedViewModelFactory(
private val getUserUseCase : GetUserUseCase,
private val removeLocalUserUseCase: RemoveLocalUserUseCase,
private val sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
private val isWearConnectedUseCase: IsWearConnectedUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [LoggedViewModel] class, reusing the objects that are passed in.
     *
     * @param modelClass the class of the [ViewModel] to be created
     * @return [LoggedViewModel]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoggedViewModel(getUserUseCase, removeLocalUserUseCase, sendApiKeyToWearUseCase, isWearConnectedUseCase) as T
    }
}