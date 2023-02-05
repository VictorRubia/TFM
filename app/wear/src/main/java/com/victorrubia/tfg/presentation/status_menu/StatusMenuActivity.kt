package com.victorrubia.tfg.presentation.status_menu

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.victorrubia.tfg.R
import com.victorrubia.tfg.presentation.user_context_menu.UserContextMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

/**
 * Activity that shows the status list menu
 */
class StatusMenuActivity :  ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent{
            StatusList{
                startActivity(UserContextMenuActivity.intent(this,it)).apply { finish() }
            }
        }
    }
}

/**
 * Composable function that shows the status list menu
 *
 * @param selectedItem function that navigates to contexts menu activity [UserContextMenuActivity] and saves the elected status
 */
@Composable
fun StatusList(selectedItem: (String) -> Unit){
    val selectedTilesNames = remember { mutableStateOf("")  }
    WearAppTheme {
        val listState = rememberScalingLazyListState()
        Scaffold(
            timeText = {
                if (!listState.isScrollInProgress) {
                    TimeText()
                }
            },
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
                state = listState
            ) {
                item {
                    ListHeader {
                        Text(text = "Registre estado")
                    }
                }
                item { statusCards("Esperando", R.drawable.status_esperando, selectedTilesNames) }
                item { statusCards("En viaje", R.drawable.en_viaje, selectedTilesNames) }
                item { statusCards("Trasbordo", R.drawable.status_trasbordo, selectedTilesNames) }
                item { statusCards("En destino", R.drawable.status_en_destino, selectedTilesNames) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringStatusChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}

/**
 * Composable function that shows the status card
 *
 * @param text name of the status
 * @param icon image of the status
 * @param selectedTileName function that saves the elected status
 */
@Composable
fun statusCards(text : String, icon : Int, selectedTileName : MutableState<String>){
    AppCard(
        appImage = {
            if(selectedTileName.value.contains(text))
                Icon(
                    imageVector = Icons.Rounded.CheckBox,
                    tint = Color.Green,
                    contentDescription = "Seleccionado",
                )
            else
                Icon(
                    imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                    contentDescription = "No seleccionado",
                )
        },
        appName = {},
        time = { },
        title = { },
        onClick = {
            if(selectedTileName.value != text){
                selectedTileName.value = text
            }
            else{
                selectedTileName.value = ""
            }
                  },
        backgroundPainter = if (selectedTileName.value == text) ColorPainter(Color.DarkGray) else CardDefaults.cardBackgroundPainter(),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    modifier = Modifier.size(width = 100.dp, height = 100.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = text.uppercase()
                )
            }
        },
    )
}

/**
 * Composable function that shows the finished registering status chip
 *
 * @param selectedItem selected item
 * @param selectedTileName function that saves the elected status
 */
@Composable
fun finishedRegisteringStatusChip(selectedItem: (String) -> Unit, selectedTileName: MutableState<String>){
    Chip(
        onClick = { selectedItem(selectedTileName.value) },
        label = {
            Text(
                text = "Registrar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Icono de terminar registro",
            )
        },
    )
}