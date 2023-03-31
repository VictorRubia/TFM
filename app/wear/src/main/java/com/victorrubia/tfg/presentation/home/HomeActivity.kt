package com.victorrubia.tfg.presentation.home

import android.Manifest
import android.app.ActivityManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.start_menu.StartMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import java.util.*
import javax.inject.Inject


private const val PERMISSION_REQUEST_BODY_SENSORS = 1

/**
 * Activity that loads and retrieves the user's data from the phone and navigates to main menu.
 */
class HomeActivity : ComponentActivity(), MessageClient.OnMessageReceivedListener {

    @Inject
    lateinit var factory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    // Navigation controller for the navigation graph
    private lateinit var navController: NavHostController
    // Variable to store connection status
    private var connectionStablised : Boolean = false
    private var iniciado : Boolean = false

    private fun navigationManager(navController: NavController){
        homeViewModel.loadingDelay().observe(this){
            if((it != null) && it && !connectionStablised){
                navController.navigate("secondScreen")
            }
        }
    }

    /**
     * Method to check if the watch is compatible with the app.
     */
    private fun navigationManagerCompatibility(navController: NavController) {
        homeViewModel.compatibility(this).observe(this) {
            if (it != null && it) {
                navController.navigate("compatibilityScreen")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        (application as Injector).createHomeSubComponent()
            .inject(this)
        homeViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.BODY_SENSORS, Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_BODY_SENSORS)
        } else {
            Log.d("PERMISSION", "ALREADY GRANTED");
        }

        Wearable.getMessageClient(this).addListener(this)

        homeViewModel.clearActivitiesAssigned()

        setContent {
            navController = rememberNavController()

            NavHost(navController, startDestination = "welcome") {
                composable("welcome") { HomeComponent({homeViewModel.requestUser()}, {navigationManager(navController)}) }
                composable("secondScreen") { ErrorConnection(navController) }
                composable("compatibilityScreen") { ErrorCompatibility() }
                composable("errorNoUser") { ErrorNoUser(navController) }
            }
            navigationManagerCompatibility(navController)
        }
    }

    // Method to handle the message received from the phone
    override fun onMessageReceived(p0: MessageEvent) {
        Log.i("MyTag", "Mensaje recibido: " + String(p0.data))
        if(p0.path.equals("error") && String(p0.data) == "errorNoUserLoggedIn"){
            connectionStablised = true
            navController.navigate("errorNoUser")
        }
        else if(p0.path.equals("api_key") && !iniciado){
            iniciado = true
            homeViewModel.saveUser(User(String(p0.data)))
            homeViewModel.getActivitiesAssigned().observe(this){
                if(it != null){
                    connectionStablised = true
                    startActivity(Intent(this, StartMenuActivity::class.java))
                    finish()
                }
            }
        }

    }

}

/**
 * Composable function to create the home screen.
 *
 * @param requestUser Function to request the user's data from the phone.
 * @param delayLogin Function to delay the navigation to the main menu.
 */
@Composable
fun HomeComponent(requestUser : () -> Unit, delayLogin : () -> Unit){
    requestUser()
    delayLogin()
    WearAppTheme {
        Scaffold(
            timeText = {
                TimeText(
                    timeTextStyle = TextStyle(fontSize = 15.sp)
                )
            },
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(width = 100.dp,height = 100.dp),
                    imageVector = Icons.Rounded.CloudSync,
                    contentDescription = "Icono de conexión",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "CONECTANDO"
                )
            }
        }
    }
}

/**
 * Composable function to create the error screen when there is no internet connection.
 *
 * @param navController Navigation controller to navigate to the main menu.
 */
@Composable
fun ErrorConnection(navController: NavController){
    WearAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(width = 35.dp,height = 35.dp),
                    imageVector = Icons.Rounded.CloudOff,
                    contentDescription = "Icono no internet",
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "SIN CONEXIÓN",
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(30.dp))
                Chip(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = { navController.navigate("welcome") },
                    enabled = true,
                    label = { Text(text = "Reintentarlo") },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Sync,
                            contentDescription = "Reintentarlo",
                            modifier = Modifier.size(ChipDefaults.IconSize)
                                .wrapContentSize(align = Alignment.Center),
                        )
                    }
                )

            }
        }
    }
}

/**
 * Composable function to create the error screen when the watch is not compatible with the app.
 *
 * @param navController Navigation controller to navigate to the main menu.
 */
@Composable
fun ErrorCompatibility(){
    WearAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(width = 45.dp,height = 45.dp),
                    imageVector = Icons.Rounded.Error,
                    contentDescription = "Icono no compatible",
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "NO ES COMPATIBLE",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

/**
 * Composable function to create the error screen when ther is no user logged
 * in the mobile companion app.
 *
 * @param navController Navigation controller to navigate to the main menu.
 */
@Composable
fun ErrorNoUser(navController: NavController){
    WearAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(width = 45.dp,height = 45.dp),
                    imageVector = Icons.Rounded.DeviceUnknown,
                    contentDescription = "Icono abrir app del movil",
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "INICIE SESIÓN\r\n EN TELÉFONO",
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Chip(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = { navController.navigate("welcome") },
                    enabled = true,
                    label = { Text(text = "Listo") },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Listo",
                            modifier = Modifier.size(ChipDefaults.IconSize)
                                .wrapContentSize(align = Alignment.Center),
                        )
                    }
                )
            }
        }
    }
}