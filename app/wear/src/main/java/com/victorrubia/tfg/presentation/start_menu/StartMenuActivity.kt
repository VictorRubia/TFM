package com.victorrubia.tfg.presentation.start_menu

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.R
import com.victorrubia.tfg.presentation.activity_type.ActivityTypeActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

/**
 * Activity that shows the start activity menu.
 */
class StartMenuActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            StartMeasure{
                startActivity(Intent(this, ActivityTypeActivity::class.java ))
                finish()
            }
        }
    }
}

/**
 * Composable function to show the start menu.
 *
 * @param startActivity Function to navigate to the activity [ActivityTypeActivity].
 */
@Composable
fun StartMeasure(startActivity : () -> Unit){
    var loading by remember { mutableStateOf(true) }
    WearAppTheme {
        Scaffold(
            timeText = { if(loading) TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(!loading){
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Button(
                    modifier = Modifier.size(100.dp,100.dp),
                    onClick = {
                        startActivity()
                        loading = !loading
                              },
                    enabled = loading
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_activity),
                        contentDescription = "Icono de comienzo de actividad",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "COMENZAR\r\nACTIVIDAD"
                )
            }
        }
    }
}