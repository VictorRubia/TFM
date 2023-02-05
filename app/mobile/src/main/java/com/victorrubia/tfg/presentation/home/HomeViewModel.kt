package com.victorrubia.tfg.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.domain.usecase.GetUserUseCase

/**
 * [ViewModel] for [HomeActivity]
 * @property getUserUseCase [GetUserUseCase]
 */
class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    /**
     * [LiveData] to retreive the user's data from the use case.
     */
    fun getUser(email : String = "", password : String = "") = liveData {
        val user = getUserUseCase.execute(email, password)
        emit(user)
    }

}