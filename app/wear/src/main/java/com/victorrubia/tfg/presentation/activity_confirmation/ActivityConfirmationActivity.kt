package com.victorrubia.tfg.presentation.activity_confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.measuring_menu.MeasuringMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

/**
 * Activity that shows the confirmation of the newly created activity.
 */
class ActivityConfirmationActivity:  ComponentActivity() {

    // Injector to inject the ViewModelFactory
    @Inject
    lateinit var factory: ActivityConfirmationViewModelFactory
    // ViewModel to get the data from the ViewModelFactory
    private lateinit var activityConfirmationViewModel: ActivityConfirmationViewModel

    // Companion object to create the Intent to start this activity
    // with the activityId
    companion object{
        private const val ActivityName = "activity_name"
        fun intent(context: Context, activityID: String)=
            Intent(context,ActivityConfirmationActivity::class.java).apply {
                putExtra(ActivityName,activityID)
            }
    }

    // activityName is the name of the activity that is created
    private val activityName : String by lazy {
        intent?.getSerializableExtra(ActivityName) as String
    }

    /**
     * Method to create the ActivityConfirmationActivity
     * @param savedInstanceState Bundle with the saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Injector to inject the SubComponent
        (application as Injector).createActivityConfirmationSubComponent()
            .inject(this)
        // ViewModel to get the data from the ViewModelFactory
        activityConfirmationViewModel = ViewModelProvider(this, factory)
            .get(ActivityConfirmationViewModel::class.java)

        // Calls new activity use case
        activityConfirmationViewModel.newActivity(activityName, Instant.now().atZone(ZoneId.of("Europe/Madrid")).toString())
            .observe(this){
                if(it != null)
                    Log.d("MyTag", "Activity started: $it")
                    startActivity(Intent(this, MeasuringMenuActivity::class.java))
                    finish()
            }

        // Set the content of the Activity
        setContent {
            ActivityConfirmation()
        }
    }
}

/**
 * Composable function that shows the confirmation of the newly created activity
 */
@Composable
fun ActivityConfirmation(){
    WearAppTheme{
        Scaffold(
            timeText = { TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.size(100.dp,100.dp),
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    enabled = false,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Icono de confirmación",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "ACTIVIDAD\r\nCOMENZADA"
                )
            }
        }
    }
}