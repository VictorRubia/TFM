package com.victorrubia.tfg.presentation.activity_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase

/**
 * Factory for creating a [ActivityConfirmationViewModel] with a constructor that takes a
 * [NewActivityUseCase]
 * @property newActivityUseCase [NewActivityUseCase]
 */
class ActivityConfirmationViewModelFactory(
    private val newActivityUseCase : NewActivityUseCase,
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [ActivityConfirmationViewModel] class
     * @return [ActivityConfirmationViewModel]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityConfirmationViewModel(newActivityUseCase) as T
    }
}