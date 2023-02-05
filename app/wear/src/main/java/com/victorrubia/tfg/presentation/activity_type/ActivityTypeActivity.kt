package com.victorrubia.tfg.presentation.activity_type

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsRailway
import androidx.compose.material.icons.rounded.Subway
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

/**
 * ActivityTypeActivity
 *
 * Activity that shows the different types of activities that can be created
 */
class ActivityTypeActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            ActivityTypeList{
                startActivity(ActivityConfirmationActivity.intent(this,it))
                finish()
            }
        }
    }
}

/**
 * Composable function that shows a list with
 * different types of activities that can be created.
 *
 * @param createActivity Function that starts the confirmation Activity
 */
@Composable
fun ActivityTypeList(createActivity: (String) -> Unit){
    var loading = remember { mutableStateOf(true) }
    WearAppTheme {
        val listState = rememberScalingLazyListState()
        Scaffold(
            timeText = { TimeText(timeTextStyle = TextStyle(fontSize = 15.sp)) },
            vignette = {
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            }
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                anchorType = ScalingLazyListAnchorType.ItemStart,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(state = listState),
                state = listState
            ) {
                item{ activityTypeChip("Viajar en bus", Icons.Rounded.DirectionsBus, loading, createActivity)}
                item{ activityTypeChip("Viajar en metro", Icons.Rounded.Subway, loading, createActivity)}
                item{ activityTypeChip("Viajar en tren", Icons.Rounded.DirectionsRailway, loading, createActivity)}
            }
        }
    }
}

/**
 * Composable function that shows a chip to create an activity
 *
 * @param text Text to show in the chip
 * @param icon Icon to show in the chip
 * @param isSelected Boolean that indicates if the chip is selected
 * @param createActivity Function that starts the confirmation Activity
 */
@Composable
fun activityTypeChip(text : String, icon : ImageVector, isSelected : MutableState<Boolean>, createActivity: (String) -> Unit){
    Chip(
        onClick = { createActivity(text)
                isSelected.value = false},
        enabled = isSelected.value,
        label = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icono de la actividad",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}