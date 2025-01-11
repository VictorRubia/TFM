package com.victorrubia.tfm.presentation.measuring_menu

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.*
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.os.SystemClock
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.liveData
import com.google.android.gms.location.*
import com.victorrubia.tfm.R
import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfm.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfm.data.model.step_measure.StepMeasure
import com.victorrubia.tfm.data.utils.Actions
import com.victorrubia.tfm.domain.usecase.*
import com.victorrubia.tfm.presentation.di.Injector
import com.victorrubia.tfm.presentation.start_menu.StartMenuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MeasuringService : Service(), SensorEventListener {

    private var wakeLock: WakeLock? = null
    private val TAG = "MeasuringService::lock"

    @Inject
    lateinit var endActivityUseCase: EndActivityUseCase

    @Inject
    lateinit var getCurrentActivityUseCase: GetCurrentActivityUseCase

    @Inject
    lateinit var savePPGMeasureUseCase: SavePPGMeasureUseCase

    @Inject
    lateinit var saveAccelerometerMeasureUseCase: SaveAccelerometerMeasureUseCase

    @Inject
    lateinit var saveGPSMeasureUseCase: SaveGPSMeasureUseCase

    @Inject
    lateinit var saveStepMeasureUseCase: SaveStepMeasureUseCase

    @Inject
    lateinit var saveSignificantMovMeasureUseCase: SaveSignificantMovMeasureUseCase

    @Inject
    lateinit var endPPGMeasureUseCase: EndPPGMeasureUseCase

    @Inject
    lateinit var endAccelerometerMeasureUseCase: EndAccelerometerMeasureUseCase

    @Inject
    lateinit var endGPSMeasureUseCase: EndGPSMeasureUseCase

    @Inject
    lateinit var endStepMeasureUseCase: EndStepMeasureUseCase

    @Inject
    lateinit var endSignificantMovMeasureUseCase: EndSignificantMovMeasureUseCase

    private lateinit var sensorManager: SensorManager
    private lateinit var heartRateSensor: Sensor
    private lateinit var accelerometerSensor: Sensor
    private lateinit var significantMovementSensor: Sensor
    private lateinit var stepCounterSensor: Sensor
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    var internetStatus = mutableStateOf(true)
    var ppgSensorStatus = true
    var accelerometerSensorStatus = true
    var significantMovementSensorStatus = true
    var stepCounterSensorStatus = true
    var ppgSensorId: Int = 0
    var accelerometerSensorId: Int = 0
    var significantMovementSensorId: Int = 0
    var stepCounterSensorId: Int = 0
    private var initialStepCount: Int? = null // To hold the initial step count value

    val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            serviceScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        if (event != null) {
                            internetStatus.value =
                                saveSignificantMovMeasureUseCase.execute(SignificantMovMeasure(0, 1, System.currentTimeMillis() - SystemClock.elapsedRealtime() + event.timestamp / 1_000_000L), it.id).value
                        }
                    }
                Log.d("Sensor", "Significant movement detected")
            }
        }
    }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                Log.d("LOCATION", "Lat: ${location.latitude} Long: ${location.longitude}")
                serviceScope.launch {
                    getCurrentActivityUseCase.execute()
                        ?.let {
                            internetStatus.value =
                                saveGPSMeasureUseCase.execute(
                                    GPSMeasure(
                                        0,
                                        location.latitude,
                                        location.longitude,
                                        System.currentTimeMillis()
                                    ), it.id
                                ).value
                        }
                }
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.d("SERVICE", "Oncreate service")
        (application as Injector).createMeasuringServiceSubComponent().inject(this)

        val notification = createNotification()
        startForeground(1, notification)
    }

    /**
     * Gets the PPG Sensor from the device. Iterates all over the device's
     * sensors to find the PPG sensor
     *
     * @param context [Context] of the application
     */
    @SuppressLint("MissingPermission")
    fun startMeasure(context: Context) {
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
        wakeLock?.acquire()

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currentSensor in sensorList) {
            if (currentSensor.stringType.contains("ppg") && ppgSensorStatus) {
                ppgSensorId = currentSensor.type
                heartRateSensor = sensorManager.getDefaultSensor(ppgSensorId)!!
                val minDelay = heartRateSensor.minDelay // en microsegundos
                val maxFrequency = 1_000_000 / minDelay // en Hz
                heartRateSensor.let {
                    sensorManager.registerListener(this, it, 10000)
                    ppgSensorStatus = false
                }
            }
            if (currentSensor.stringType.contains("accelerometer") && accelerometerSensorStatus) {
                accelerometerSensorId = currentSensor.type
                accelerometerSensor = sensorManager.getDefaultSensor(accelerometerSensorId)!!
                accelerometerSensor.let {
                    sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, 31250)
                    accelerometerSensorStatus = false
                }
            }
            if (currentSensor.stringType.contains("significant_motion") && significantMovementSensorStatus) {
                significantMovementSensorId = currentSensor.type
                significantMovementSensor = sensorManager.getDefaultSensor(significantMovementSensorId)!!
                significantMovementSensor.let {
                    sensorManager.requestTriggerSensor(triggerEventListener, it)
                    significantMovementSensorStatus = false
                }
            }
        }

        stepCounterSensorId = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)?.type ?: 0
        stepCounterSensor = sensorManager.getDefaultSensor(stepCounterSensorId)!!
        stepCounterSensor.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL, 30000000)
            stepCounterSensorStatus = false
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                Log.d("LOCATION", "Lat: ${it.latitude} Long: ${it.longitude}")
            } ?: run {
                Log.d("LOCATION", "Location is null")
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action

            if (action == Actions.START.name) {
                startMeasure(this)
            } else if (action == Actions.STOP.name) {
                Log.d("SERVICE", "Se recibe señal de parar el serivcio")
                stopService()
            } else {
                Log.d("SERVICE", "This should never happen. No action in the received intent")
            }
        } else {
            Log.d(
                "SERVICE",
                "with a null intent. It has been probably restarted by the system."
            )
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    /**
     * Unregisters the PPG sensor, ends the current activity and ends the current PPG measure.
     */
    fun endActivity() = liveData {
        try {
            getCurrentActivityUseCase.execute()?.let { endPPGMeasureUseCase.execute(it.id) }
            getCurrentActivityUseCase.execute()?.let { endAccelerometerMeasureUseCase.execute(it.id) }
            getCurrentActivityUseCase.execute()?.let { endGPSMeasureUseCase.execute(it.id) }
            getCurrentActivityUseCase.execute()?.let { endStepMeasureUseCase.execute(it.id) }
            getCurrentActivityUseCase.execute()?.let { endSignificantMovMeasureUseCase.execute(it.id) }
            val activity = endActivityUseCase.execute()
            emit(activity)
        } catch (e: Exception) {
            Log.d("SERVICE", "Service stopped without being started: " + e.message)
        }
    }

    private fun stopService() {
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
        // Guardar estado del servicio como STOP en SharedPreferences
        val sharedPrefStop = getSharedPreferences("EstresEnActividades", Context.MODE_PRIVATE)
        with(sharedPrefStop.edit()) {
            putString("service_state", "STOP")
            apply()
        }
        stopSensor()
        stopSelf()
        endActivity().observeForever { activity ->
            if (activity != null) {
                Log.d("SERVICE", "Activity Ended: $activity")
                // Remove the observer after it has been triggered
                endActivity().removeObserver { }
            }
        }    }

    /**
     * Stops the PPG sensor
     */
    private fun stopSensor() {
        sensorManager.unregisterListener(this, heartRateSensor)
        sensorManager.unregisterListener(this, accelerometerSensor)
        sensorManager.unregisterListener(this, significantMovementSensor)
        sensorManager.unregisterListener(this, stepCounterSensor)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        stopForeground(STOP_FOREGROUND_REMOVE) // Remove the foreground notification

        val intent = Intent(this, StartMenuActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        onDestroy()
    }

    /**
     * When we register a sensor event for the type of sensor we are looking for (PPG),
     * we execute the use case to save it.
     *
     * @param event [SensorEvent] of the sensor
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == ppgSensorId) {
            val measureValue = event.values[0].toInt()
//            val timestampValue = event.timestamp
            val timestampValue = System.currentTimeMillis() - SystemClock.elapsedRealtime() + event.timestamp / 1_000_000L
            serviceScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            savePPGMeasureUseCase.execute(PPGMeasure(0, measureValue, timestampValue), it.id).value
                    }
            }
        }
        if (event.sensor.type == stepCounterSensorId) {
            val currentStepCount = event.values[0].toInt()

            if (initialStepCount == null) {
                // Set initial step count if not already set
                initialStepCount = currentStepCount
            }

            // Calculate the incremental steps
            val stepsTaken = currentStepCount - (initialStepCount ?: currentStepCount)

            serviceScope.launch {
                getCurrentActivityUseCase.execute()?.let { activity ->
                    val stepMeasure = StepMeasure(
                        0,
                        stepsTaken,
                        System.currentTimeMillis() - SystemClock.elapsedRealtime() + event.timestamp / 1_000_000L
                    )
                    internetStatus.value = saveStepMeasureUseCase.execute(stepMeasure, activity.id).value
                }
            }

            Log.d("Sensor", "Step counter detected: $currentStepCount, Steps taken: $stepsTaken")
        }
        if (event.sensor.type == accelerometerSensorId){
            serviceScope.launch {
                getCurrentActivityUseCase.execute()
                    ?.let {
                        internetStatus.value =
                            saveAccelerometerMeasureUseCase.execute(AccelerometerMeasure(0, event.values[0], event.values[1], event.values[2], System.currentTimeMillis() - SystemClock.elapsedRealtime() + event.timestamp / 1_000_000L), it.id).value
                    }
            }
        }

    }

    /**
     * Not used
     */
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
        // Guardar estado del servicio como STOP en SharedPreferences
        val sharedPref = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("service_state", "STOP")
            apply()
        }
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            notificationChannelId,
            "Endless Service notifications channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        channel.description = "Endless Service channel"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        notificationManager.createNotificationChannel(channel)

        // Existing Stop Service Action
        val stopServiceIntent = Intent(this, MeasuringService::class.java).apply {
            action = Actions.STOP.name
        }

        val stopIntent = PendingIntent.getService(
            this,
            0,
            stopServiceIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                IconCompat.createWithResource(this, R.drawable.stop_24),
                "Parar",
                stopIntent
            ).build()

        val openAppIntent = Intent(this, MeasuringMenuActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val openAppPendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openAppAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                IconCompat.createWithResource(this, R.drawable.open),
                "Abrir",
                openAppPendingIntent
            ).build()

        val builder = NotificationCompat.Builder(this, notificationChannelId)

        return builder
            .setContentTitle("Estrés en Actividades")
            .setContentText("Recogiendo datos")
            .setSmallIcon(R.drawable.ic_logo_mini_app_bn)
            .setTicker("CSV")
            .addAction(stopAction)
            .addAction(openAppAction) // Add the new action here
            .build()
    }
}