package com.victorrubia.tfg.presentation.logged

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [LoggedActivity]
 * @property getUserUseCase [GetUserUseCase]
 * @property isWearConnectedUseCase [IsWearConnectedUseCase]
 * @property removeLocalUserUseCase [RemoveLocalUserUseCase]
 * @property sendApiKeyToWearUseCase [SendApiKeyToWearUseCase]
 */
class LoggedViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val removeLocalUserUseCase: RemoveLocalUserUseCase,
    private val sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
    private val isWearConnectedUseCase: IsWearConnectedUseCase,
) : ViewModel(){

    /**
     * [LiveData] for sending the logged user's data to the wear device
     */
    fun sendApiKeyToWear(){
        CoroutineScope(Dispatchers.IO).launch {
            sendApiKeyToWearUseCase.execute()
        }
    }

    /**
     * Function to check if the wear device is connected
     */
    fun isWearConnected() = liveData {
        emit(isWearConnectedUseCase.execute())
    }

    /**
     * [LiveData] for getting the logged user's data from the use case
     */
    fun getUser(email : String = "", password : String = "") = liveData {
        val user = getUserUseCase.execute(email, password)
        emit(user)
    }

    /**
     * [LiveData] for removing the logged user's data from the use case
     */
    fun removeLocalUser() {
        viewModelScope.launch{
            removeLocalUserUseCase.execute()
        }
    }

}