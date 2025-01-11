package com.victorrubia.tfm.presentation.activity_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfm.domain.usecase.*

/**
 * Factory for creating a [HomeViewModel] with a constructor that takes a [GetUserUseCase] and an
 * [NewActivityUseCase].
 * @property newActivityUseCase A [NewActivityUseCase] instance.
 * @property saveUserUseCase A [SaveUserUseCase] instance.
 */
class ActivityTypeViewModelFactory(
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the [HomeViewModel] class, reusing the objects that are passed in.
     * @param modelClass The class of the [ViewModel] to create.
     * @return A new instance of the [HomeViewModel] class.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityTypeViewModel(getActivitiesAssignedUseCase) as T
    }
}