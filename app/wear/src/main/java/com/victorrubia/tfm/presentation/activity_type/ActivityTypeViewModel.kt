package com.victorrubia.tfm.presentation.activity_type

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.domain.usecase.ClearActivitiesAssignedUseCase
import com.victorrubia.tfm.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfm.domain.usecase.RequestUserUseCase
import com.victorrubia.tfm.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity]
 * @property requestUserUseCase [RequestUserUseCase]
 * @property saveUserUseCase [SaveUserUseCase]
 */
class ActivityTypeViewModel(
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
) : ViewModel() {

    fun getActivitiesAssigned() = liveData(Dispatchers.IO) {
        val result = getActivitiesAssignedUseCase.execute()
        emit(result)
    }

}