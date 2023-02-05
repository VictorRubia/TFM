package com.victorrubia.tfg.presentation.activity_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase

/**
 * ViewModel for [ActivityConfirmationActivity]
 * @property newActivityUseCase UseCase to create a new activity
 */
class ActivityConfirmationViewModel(
    private val newActivityUseCase: NewActivityUseCase,
) : ViewModel() {

    /**
     * LiveData to create a new activity with the given parameters
     *
     * @param name Name of the activity
     * @param startTimestamp Starting timestamp of the activity
     * @return LiveData with the result of the use case
     */
    fun newActivity(name: String, startTimestamp: String) = liveData {
        val activity = newActivityUseCase.execute(name, startTimestamp)
        emit(activity)
    }
}