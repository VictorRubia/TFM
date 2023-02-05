package com.victorrubia.tfg.presentation.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

/**
 * Factory for creating a [EntryViewModel] with a constructor that takes a [GetUserUseCase].
 * @property getUserUseCase A [GetUserUseCase] for getting the user.
 */
class EntryViewModelFactory(
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [EntryViewModel] class, reusing the objects that are passed in.
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the [EntryViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EntryViewModel(getUserUseCase) as T
    }
}