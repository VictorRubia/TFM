package com.victorrubia.tfm.presentation.home

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
class HomeViewModel(
    private val requestUserUseCase: RequestUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
    private val clearActivitiesAssignedUseCase: ClearActivitiesAssignedUseCase
) : ViewModel() {

    // Sensor variables
    private lateinit var sensorManager: SensorManager
    var sensorStatus = mutableStateOf(false)

    fun loadingDelay() = liveData {
        kotlinx.coroutines.delay(40000)
        emit(true)
    }

    /**
     * Determines if device is compatible with the app
     *
     * @param context [Context]
     */
    fun compatibility(context : Context) = liveData {
        var prueba : MutableLiveData<Boolean> = MutableLiveData(true)
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if(currentSensor.stringType.contains("ppg") && !sensorStatus.value){
                sensorStatus.value = true
                prueba.value = false
            }
        }
        emit(prueba.value)
    }

//    fun getActivitiesAssigned(){
//        CoroutineScope(Dispatchers.IO).launch {
//            getActivitiesAssignedUseCase.execute()
//        }
//    }

    fun getActivitiesAssigned() = liveData {
        emit(getActivitiesAssignedUseCase.execute())
    }

    fun clearActivitiesAssigned(){
        CoroutineScope(Dispatchers.IO).launch {
            clearActivitiesAssignedUseCase.execute()
        }
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