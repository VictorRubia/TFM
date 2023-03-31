package com.victorrubia.tfg.presentation.measuring_menu

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
import com.victorrubia.tfg.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfg.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfg.data.model.step_measure.StepMeasure
import com.victorrubia.tfg.domain.usecase.*
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
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val saveAccelerometerMeasureUseCase: SaveAccelerometerMeasureUseCase,
    private val saveGPSMeasureUseCase: SaveGPSMeasureUseCase,
    private val saveStepMeasureUseCase: SaveStepMeasureUseCase,
    private val saveSignificantMovMeasureUseCase: SaveSignificantMovMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase,
    private val endAccelerometerMeasureUseCase: EndAccelerometerMeasureUseCase,
    private val endGPSMeasureUseCase: EndGPSMeasureUseCase,
    private val endStepMeasureUseCase: EndStepMeasureUseCase,
    private val endSignificantMovMeasureUseCase: EndSignificantMovMeasureUseCase,
) : ViewModel(), SensorEventListener {

    // Sensor manager to get the PPG sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor : Sensor
    private lateinit var accelerometerSensor : Sensor
    private lateinit var significantMovementSensor : Sensor
    private lateinit var stepCounterSensor : Sensor
    var internetStatus = mutableStateOf(true)
    var ppgSensorStatus = true
    var accelerometerSensorStatus = true
    var significantMovementSensorStatus = true
    var stepCounterSensorStatus = true
    var ppgSensorId : Int = 0
    var accelerometerSensorId : Int = 0
    var significantMovementSensorId : Int = 0
    var stepCounterSensorId : Int = 0
    val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            viewModelScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            saveSignificantMovMeasureUseCase.execute(SignificantMovMeasure(0, 1, Date()), it.id).value
                    }
            }
            Log.d("Sensor", "Significant movement detected")
        }
    }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (location in locationResult.locations) {
                Log.d("LOCATION", "Lat: ${location.latitude} Long: ${location.longitude}")
                viewModelScope.launch {
                    getCurrentActivityUseCase.execute()
                        ?.let {
                            internetStatus.value =
                                saveGPSMeasureUseCase.execute(GPSMeasure(0, location.latitude, location.longitude, Date()), it.id).value
                        }
                }
            }
        }
    }


    /**
     * Gets the PPG Sensor from the device. Iterates all over the device's
     * sensors to find the PPG sensor
     *
     * @param context [Context] of the application
     */
    @SuppressLint("MissingPermission")
    fun startMeasure(context : Context) {
        sensorManager = context.getSystemService(ComponentActivity.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if(currentSensor.stringType.contains("ppg") && ppgSensorStatus){
                ppgSensorId = currentSensor.type
                heartRateSensor = sensorManager.getDefaultSensor(ppgSensorId)
                sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
                ppgSensorStatus = false
            }
            if(currentSensor.stringType.contains("accelerometer")  && accelerometerSensorStatus){
                accelerometerSensorId = currentSensor.type
                accelerometerSensor = sensorManager.getDefaultSensor(accelerometerSensorId)
                sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL, 31250)
                accelerometerSensorStatus = false
            }
        }
        significantMovementSensorId = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION).type
        significantMovementSensor = sensorManager.getDefaultSensor(significantMovementSensorId)
//        sensorManager.registerListener(this, significantMovementSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.requestTriggerSensor(triggerEventListener, significantMovementSensor)
        significantMovementSensorStatus = false

        stepCounterSensorId = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER).type
        stepCounterSensor = sensorManager.getDefaultSensor(stepCounterSensorId)
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL, 30000000)
        stepCounterSensorStatus = false

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            Log.d("LOCATION", "Lat: ${it.latitude} Long: ${it.longitude}")
        }

        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.create().setInterval(10000).setFastestInterval(5000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
            locationCallback,
            Looper.getMainLooper()
        )

    }

    /**
     * Unregisters the PPG sensor, ends the current activity and ends the current PPG measure.
     */
    fun endActivity() = liveData{
        stopSensor()
        getCurrentActivityUseCase.execute()?.let { endPPGMeasureUseCase.execute(it.id) }
        getCurrentActivityUseCase.execute()?.let { endAccelerometerMeasureUseCase.execute(it.id) }
        getCurrentActivityUseCase.execute()?.let { endGPSMeasureUseCase.execute(it.id) }
        getCurrentActivityUseCase.execute()?.let { endStepMeasureUseCase.execute(it.id) }
        getCurrentActivityUseCase.execute()?.let { endSignificantMovMeasureUseCase.execute(it.id) }
        val activity = endActivityUseCase.execute()
        emit(activity)
    }

    /**
     * Stops the PPG sensor
     */
    private fun stopSensor(){
        sensorManager.unregisterListener(this, heartRateSensor)
        sensorManager.unregisterListener(this, accelerometerSensor)
        sensorManager.unregisterListener(this, significantMovementSensor)
        sensorManager.unregisterListener(this, stepCounterSensor)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    /**
     * When we register a sensor event for the type of sensor we are looking for (PPG),
     * we execute the use case to save it.
     *
     * @param event [SensorEvent] of the sensor
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == ppgSensorId) {
            viewModelScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            savePPGMeasureUseCase.execute(PPGMeasure(0, event.values[0].toInt(), Date()), it.id).value
                    }
            }
        }
        if (event.sensor.type == accelerometerSensorId){
            viewModelScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            saveAccelerometerMeasureUseCase.execute(AccelerometerMeasure(0, event.values[0], event.values[1], event.values[2], Date()), it.id).value
                    }
            }
        }
        if(event.sensor.type == stepCounterSensorId){
            viewModelScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            saveStepMeasureUseCase.execute(StepMeasure(0, event.values[0].toInt(), Date()), it.id).value
                    }
            }
            Log.d("Sensor", "Step counter detected: ${event.values[0]}")
        }
    }

    /**
     * Not used
     */
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}