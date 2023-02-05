package com.victorrubia.tfg.presentation.entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

/**
 * [ViewModel] for [EntryActivity]
 * @property getUserUseCase [GetUserUseCase]
 */
class EntryViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    /**
     * [liveData] that contains the user data if is previously logged in
     */
    fun getUser() = liveData {
        val user = getUserUseCase.execute("", "")
        emit(user)
    }
}