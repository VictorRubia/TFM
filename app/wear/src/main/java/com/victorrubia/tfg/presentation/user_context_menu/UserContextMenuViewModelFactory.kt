package com.victorrubia.tfg.presentation.user_context_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.*


class UserContextMenuViewModelFactory(
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [HomeViewModel] class, reusing the objects that are passed in.
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the [HomeViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserContextMenuViewModel(getActivitiesAssignedUseCase, getCurrentActivityUseCase) as T
    }
}