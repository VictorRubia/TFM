package com.victorrubia.tfg.presentation.home

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity]
 * @property requestUserUseCase [RequestUserUseCase]
 * @property saveUserUseCase [SaveUserUseCase]
 */
class HomeViewModel(
    private val requestUserUseCase: RequestUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {

    // Sensor variables
    private lateinit var sensorManager: SensorManager
    var sensorStatus = mutableStateOf(false)

    fun loadingDelay() = liveData {
        kotlinx.coroutines.delay(15000)
        emit(true)
    }

    /**
     * Determines if device is compatible with the app
     *
     * @param context [Context]
     */
    fun compatibility(context : Context) = liveData {
        var prueba : MutableLiveData<Boolean> = MutableLiveData(true)
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            Log.d("SENSORES", "${currentSensor.stringType} - ${currentSensor.type}")
            if(currentSensor.stringType.contains("ppg") && !sensorStatus.value){
                sensorStatus.value = true
                prueba.value = false
            }
        }
        emit(prueba.value)
    }

    /**
     * Executes [RequestUserUseCase]
     */
    fun requestUser(){
        CoroutineScope(Dispatchers.IO).launch {
            requestUserUseCase.execute()
        }
    }

    /**
     * Executes [SaveUserUseCase]
     *
     * @param user [User] to save
     */
    fun saveUser(user : User){
        CoroutineScope(Dispatchers.IO).launch {
            saveUserUseCase.execute(user)
        }
    }

}