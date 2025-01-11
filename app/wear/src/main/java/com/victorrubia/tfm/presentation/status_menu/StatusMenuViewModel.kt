package com.victorrubia.tfm.presentation.status_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfm.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfm.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfm.domain.usecase.RequestUserUseCase
import com.victorrubia.tfm.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.Dispatchers

/**
 * [ViewModel] for [HomeActivity]
 * @property requestUserUseCase [RequestUserUseCase]
 * @property saveUserUseCase [SaveUserUseCase]
 */
class StatusMenuViewModel(
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase
) : ViewModel() {

    fun getActivitiesAssigned() = liveData(Dispatchers.IO) {
        val result = getActivitiesAssignedUseCase.execute()
        emit(result)
    }

    fun getCurrentActivity() = liveData(Dispatchers.IO) {
        val result = getCurrentActivityUseCase.execute()
        emit(result)
    }

}