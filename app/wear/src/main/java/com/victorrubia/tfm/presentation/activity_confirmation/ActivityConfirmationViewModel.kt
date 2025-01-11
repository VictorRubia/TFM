package com.victorrubia.tfm.presentation.activity_confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfm.domain.usecase.NewActivityUseCase

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
    fun newActivity(activityId: Int, startTimestamp: String) = liveData {
        val activity = newActivityUseCase.execute(activityId, startTimestamp)
        emit(activity)
    }
}