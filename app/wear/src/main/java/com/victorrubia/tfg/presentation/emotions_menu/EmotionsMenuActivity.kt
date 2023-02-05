package com.victorrubia.tfg.presentation.emotions_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddReaction
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme

/**
 * Activity that shows the emotions list menu with a previous
 * announcement screen informing the user about the context.
 */
class EmotionsMenuActivity: ComponentActivity() {

    // View model for the emotions menu
    private lateinit var emotionsMenuViewModel: EmotionsMenuViewModel

    // companion object to create the Intent to start this activity
    // and recovering the previously collected information.
    companion object{
        private const val STATUS_TILE = "statusTileSelected"
        private const val CONTEXT_TILES = "contextTilesSelected"
        fun intent(context: Context, statusTileSelected: String, contextTilesSelected: ArrayList<String>)=
            Intent(context, EmotionsMenuActivity::class.java).apply {
                putExtra(STATUS_TILE,statusTileSelected)
                putStringArrayListExtra(CONTEXT_TILES, contextTilesSelected)
            }
    }

    // State variables to store the previously collected information
    private val statusTilesSelected : String by lazy {
        intent?.getSerializableExtra(STATUS_TILE) as String
    }

    @Suppress("UNCHECKED_CAST")
    private val contextTilesSelected : ArrayList<String> by lazy {
        intent?.getSerializableExtra(CONTEXT_TILES) as ArrayList<String>
    }

    /**
     * Method to start the feelings activity and recover the previously collected information.
     * @param emotion Selected emotion
     */
    fun startFeelings(emotion: String){
        startActivity(FeelingsMenuActivity.intent(this,statusTilesSelected, contextTilesSelected, emotion))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        emotionsMenuViewModel = ViewModelProvider(this)
            .get(EmotionsMenuViewModel::class.java)

        setContent{
            val navController = rememberNavController()
            NavHost(navController, startDestination = "announcement") {
                composable("announcement") { emotionAnnouncement() }
                composable("emotionsList") { EmotionsList{ startFeelings(it) } }
            }
            emotionsMenuViewModel.delayAnnouncement().observe(this){
                if(it != null && it){
                    navController.navigate("emotionsList")
                }
            }
        }
    }
}

/**
 * Composable function to show list of emotions.
 *
 * @param selectedItem Function to call when user is finished selecting emotion.
 */
@Composable
fun EmotionsList(selectedItem: (String) -> Unit){
    val selectedTilesName = remember { mutableStateOf("") }
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
                        Text(text = "Registre emoción")
                    }
                }
                item { emotionsCards("Felicidad", "😀", selectedTilesName) }
                item { emotionsCards("Tristeza", "😢", selectedTilesName) }
                item { emotionsCards("Miedo", "😱", selectedTilesName) }
                item { emotionsCards("Ira", "😠", selectedTilesName) }
                item { emotionsCards("Sorpresa", "😯", selectedTilesName) }
                item { emotionsCards("Asco", "😒", selectedTilesName) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringEmotionsChip(selectedItem, selectedTilesName) }
            }
        }
    }
}

/**
 * Composable function to show an announcement of application context
 */
@Composable
fun emotionAnnouncement(){
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
                        imageVector = Icons.Rounded.AddReaction,
                        contentDescription = "Icono de emoción",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "AHORA REGISTRE\r\nEMOCIÓN"
                )
            }
        }
    }
}

/**
 * Composable function to show a card with the emotion name and icon
 *
 * @param text text to show in the card
 * @param icon icon to show in the card
 * @param selectedTileName name of the selected tile
 */
@Composable
fun emotionsCards(text : String, icon : String, selectedTileName : MutableState<String>){
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
                Text(
                    textAlign = TextAlign.Center,
                    text = icon.uppercase(),
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth().height(75.dp)
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
 * Composable function to show a chip to end registering the emotion.
 *
 * @param selectedItem function to start a new activity [FeelingsMenuActivity]
 * @param selectedTileName name of the selected tile
 */
@Composable
fun finishedRegisteringEmotionsChip(selectedItem: (String) -> Unit, selectedTileName: MutableState<String>){
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