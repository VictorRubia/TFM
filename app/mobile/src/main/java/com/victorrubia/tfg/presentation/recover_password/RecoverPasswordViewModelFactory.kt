package com.victorrubia.tfg.presentation.recover_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase

/**
 * Factory for creating a [RecoverPasswordViewModel] with a constructor that takes a [RecoverPasswordUseCase].
 * @property recoverPasswordUseCase [RecoverPasswordUseCase]
 */
class RecoverPasswordViewModelFactory(
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [RecoverPasswordViewModel] class, reusing the objects that are passed in.
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the [RecoverPasswordViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return RecoverPasswordViewModel(recoverPasswordUseCase) as T
    }

}