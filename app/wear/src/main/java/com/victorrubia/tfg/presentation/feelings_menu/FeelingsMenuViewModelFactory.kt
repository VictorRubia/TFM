package com.victorrubia.tfg.presentation.feelings_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase

/**
 * Factory for creating a [FeelingsMenuViewModel] with a constructor
 * that takes a [GetCurrentActivityUseCase] and an [AddTagUseCase]
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase]
 * @property addTagUseCase [AddTagUseCase]
 */
class FeelingsMenuViewModelFactory(
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val addTagUseCase: AddTagUseCase,
) : ViewModelProvider.Factory {

    /**
     * Creates a [FeelingsMenuViewModel]
     * @param modelClass [Class] of the [ViewModel]
     * @return [FeelingsMenuViewModel]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeelingsMenuViewModel(getCurrentActivityUseCase, addTagUseCase) as T
    }
}