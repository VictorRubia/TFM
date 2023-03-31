package com.victorrubia.tfg.presentation.user_context_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * ViewModel for the [UserContextMenuFragment].
 */
class UserContextMenuViewModel(
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase
) : ViewModel()  {

    /**
     * Delay for showing the announcement of register context menu.
     */
    fun delayAnnouncement() = liveData<Boolean> {
        delay(2500)
        emit(true)
    }

    fun getActivitiesAssigned() = liveData(Dispatchers.IO) {
        val result = getActivitiesAssignedUseCase.execute()
        emit(result)
    }

    fun getCurrentActivity() = liveData(Dispatchers.IO) {
        val result = getCurrentActivityUseCase.execute()
        emit(result)
    }

}