package com.victorrubia.tfg.presentation.emotions_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

/**
 * ViewModel for [EmotionsMenuActivity]
 */
class EmotionsMenuViewModel : ViewModel()  {

    /**
     * Function to show the announcement for a certain time
     */
    fun delayAnnouncement() = liveData<Boolean> {
        delay(2500)
        emit(true)
    }

}