package com.victorrubia.tfm.presentation.measuring_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.data.utils.Actions
import com.victorrubia.tfm.presentation.di.Injector
import com.victorrubia.tfm.presentation.home.HomeViewModel
import com.victorrubia.tfm.presentation.start_menu.StartMenuActivity
import com.victorrubia.tfm.presentation.status_menu.StatusMenuActivity
import com.victorrubia.tfm.ui.theme.WearAppTheme
import javax.inject.Inject

/**
 * Activity that shows the menu to register tags or to stop measuring.
 */
class MeasuringMenuActivity: ComponentActivity() {

    // Injector to inject the ViewModelFactory
    @Inject
    lateinit var factory: MeasuringMenuViewModelFactory
    // ViewModel
    private lateinit var measuringMenuViewModel: MeasuringMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val serviceIntent = Intent(this, MeasuringService::class.java).apply {
            this.setAction(Actions.START.name)
        }
        startForegroundService(serviceIntent)

        val sharedPref = getSharedPreferences("EstresEnActividades", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("service_state", "START")
            apply()
        }

        (application as Injector).createMeasuringMenuSubComponent()
            .inject(this)
        measuringMenuViewModel = ViewModelProvider(this, factory)
            .get(MeasuringMenuViewModel::class.java)

        setContent {
            MainMenu( remember { measuringMenuViewModel.internetStatus },{
                measuringMenuViewModel.endActivity().observe(this){
                    if(it!=null){
                        Log.d("MyTag", "Activity Ended: $it")

                        // Enviar Intent para detener el servicio (renombrado a stopServiceIntent)
                        val stopServiceIntent = Intent(this@MeasuringMenuActivity, MeasuringService::class.java).apply {
                            this.setAction(Actions.STOP.name)
                        }
                        startForegroundService(stopServiceIntent)

                        startActivity(Intent(this@MeasuringMenuActivity, StartMenuActivity::class.java))
                        finish()
                    }
                }
            },
                {
                    startActivity(Intent(this, StatusMenuActivity::class.java))
                }
            )
        }
    }
}

/**
 * Composable function that shows the main menu.
 *
 * @param internetStatus Boolean that indicates if the internet is available.
 * @param stopMeasuring Function that stops the measuring.
 * @param registerStatus Function that registers the status using tags.
 */
@Composable
fun MainMenu(internetStatus: MutableState<Boolean>, stopMeasuring: () -> Unit, registerStatus: () -> Unit){
    var loading = remember { mutableStateOf(true) }
    WearAppTheme{
        val contentModifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 8.dp)
        val iconModifier = Modifier.size(24.dp).wrapContentSize(align = Alignment.Center)

        Scaffold(
            timeText = { if(internetStatus.value) TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(!internetStatus.value){
                    noInternet()
                }
                else{
                    Spacer(Modifier.height(40.dp))
                }
                registerStatusChip(contentModifier, iconModifier, registerStatus)
                stopActivityButton(contentModifier, internetStatus, stopMeasuring, loading)
            }
        }
    }
}

/**
 * Composable function that shows the button to stop the measuring.
 *
 * @param contentModifier Modifier to apply to the button.
 * @param internetStatus Boolean that indicates if the internet is available.
 * @param stopMeasuring Function that stops the measuring.
 * @param loading MutableState that indicates if the button has been pressed.
 */
@Composable
fun stopActivityButton(modifier : Modifier = Modifier, internetStatus: MutableState<Boolean>, stopMeasuring: () -> Unit, isStopped : MutableState<Boolean>){
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = { stopMeasuring()
                      isStopped.value = false
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            enabled = isStopped.value and internetStatus.value
        ) {
            Icon(
                imageVector = Icons.Rounded.StopCircle,
                contentDescription = "Para la medición para la actividad",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            text = "PARAR MEDICIÓN"
        )
    }

}

/**
 * Composable function that shows the button to register tags.
 *
 * @param contentModifier Modifier to apply to the button.
 * @param iconModifier Modifier to apply to the icon.
 * @param registerStatus Function that registers the status using tags.
 */
@Composable
fun registerStatusChip(modifier: Modifier = Modifier, iconModifier: Modifier = Modifier, registerStatus: () -> Unit){
    Chip(
        modifier = modifier,
        onClick = { registerStatus() },
        label = {
            Text(
                text = "Registrar estado",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "Icono de registrar estado",
                modifier = iconModifier
            )
        },
    )
}

/**
 * Composable function that shows up when internet connection
 * has been lost.
 */
@Composable
fun noInternet(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(width = 25.dp, height = 25.dp),
            imageVector = Icons.Rounded.CloudOff,
            contentDescription = "Icono no internet",
        )
        Text(
            textAlign = TextAlign.Center,
            text = "SIN CONEXIÓN",
            fontSize = 13.sp,
        )
    }
}