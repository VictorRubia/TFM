package com.victorrubia.tfm.presentation.measuring_menu

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.getSystemService
import androidx.lifecycle.*
import com.google.android.gms.location.*
import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfm.data.model.step_measure.StepMeasure
import com.victorrubia.tfm.domain.usecase.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * [ViewModel] for the [MeasuringMenuActivity]
 *
 * @property endActivityUseCase [EndActivityUseCase] to end the current activity
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase] to get the current activity
 * @property savePPGMeasureUseCase [SavePPGMeasureUseCase] to save a new PPG measure
 * @property endPPGMeasureUseCase [EndPPGMeasureUseCase] to end the current PPG measure
 */
class MeasuringMenuViewModel(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
) : ViewModel() {

    // Sensor manager to get the PPG sensor
    var internetStatus = mutableStateOf(true)

    fun endActivity() = liveData{
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

}