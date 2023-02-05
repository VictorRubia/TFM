package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

/**
 * Factory for creating a [HomeViewModel] with a constructor that takes a [GetUserUseCase] and an
 * [NewActivityUseCase].
 * @property getUserUseCase [GetUserUseCase] use case for getting the user.
 */
class HomeViewModelFactory(
    private val getUserUseCase : GetUserUseCase,
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [HomeViewModel] class, reusing the objects that are passed in.
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the [HomeViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getUserUseCase) as T
    }
}