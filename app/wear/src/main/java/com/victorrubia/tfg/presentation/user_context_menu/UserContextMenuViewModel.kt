package com.victorrubia.tfg.presentation.user_context_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

/**
 * ViewModel for the [UserContextMenuFragment].
 */
class UserContextMenuViewModel : ViewModel()  {

    /**
     * Delay for showing the announcement of register context menu.
     */
    fun delayAnnouncement() = liveData<Boolean> {
        delay(2500)
        emit(true)
    }

}