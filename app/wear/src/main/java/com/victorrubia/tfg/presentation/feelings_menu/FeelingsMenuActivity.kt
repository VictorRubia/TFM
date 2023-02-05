package com.victorrubia.tfg.presentation.feelings_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddReaction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.*
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.emotions_menu.emotionsCards
import com.victorrubia.tfg.presentation.emotions_menu.finishedRegisteringEmotionsChip
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

/**
 * Activity that shows the menu of feelings.
 */
class FeelingsMenuActivity: ComponentActivity() {

    // companion object to create the Intent to start this activity
    // and recovering the previously collected information.
    companion object{
        private const val STATUS_TILE = "statusTileSelected"
        private const val CONTEXT_TILES = "contextTilesSelected"
        private const val EMOTION_TILE = "emotionTileSelected"
        fun intent(context: Context, statusTile: String, contextTiles: ArrayList<String>, emotionTile: String)=
            Intent(context, FeelingsMenuActivity::class.java).apply {
                putExtra(STATUS_TILE,statusTile)
                putStringArrayListExtra(CONTEXT_TILES,contextTiles)
                putExtra(EMOTION_TILE,emotionTile)
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

    private val emotionTilesSelected : String by lazy {
        intent?.getSerializableExtra(EMOTION_TILE) as String
    }

    // Injector to inject the ViewModelFactory
    @Inject
    lateinit var factory: FeelingsMenuViewModelFactory
    // ViewModel to interact with the database
    private lateinit var feelingsMenuViewModel: FeelingsMenuViewModel

    /**
     * Executes the register of tags and ends this Activity.
     * @param feeling the feeling that the user has selected.
     */
    private fun endRegister(feeling: String){
        feelingsMenuViewModel.addTag(statusTilesSelected, contextTilesSelected, emotionTilesSelected, feeling)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createFeelingsMenuSubComponent()
            .inject(this)
        feelingsMenuViewModel = ViewModelProvider(this, factory)
            .get(FeelingsMenuViewModel::class.java)

        setContent{
            val navController = rememberNavController()
            NavHost(navController, startDestination = "announcement") {
                composable("announcement") { feelingAnnouncement() }
                composable("feelingsList") { FeelingsList{ endRegister(it) } }
            }
            feelingsMenuViewModel.delayAnnouncement().observe(this){
                if(it != null && it){
                    navController.navigate("feelingsList")
                }
            }
        }
    }
}

/**
 * Composable function that shows the announcement of the feeling register menu.
 */
@Composable
fun feelingAnnouncement(){
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
                        contentDescription = "Icono de sentimiento",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "AHORA REGISTRE\r\nSENTIMIENTO"
                )
            }
        }
    }
}

/**
 * Composable function that shows the list of feelings.
 * @param selectedItem function that receives the selected item and terminates the registering.
 */
@Composable
fun FeelingsList(selectedItem: (String) -> Unit){
    val selectedTilesNames = remember { mutableStateOf("") }
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
                        Text(text = "Registre sentimiento")
                    }
                }
                item { emotionsCards("Nervioso", "😬", selectedTilesNames) }
                item { emotionsCards("Tranquilo", "😇", selectedTilesNames) }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringEmotionsChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}