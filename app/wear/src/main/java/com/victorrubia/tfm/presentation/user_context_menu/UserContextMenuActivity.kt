package com.victorrubia.tfm.presentation.user_context_menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.EmojiPeople
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.victorrubia.tfm.presentation.di.Injector
import com.victorrubia.tfm.presentation.emotions_menu.EmotionsMenuActivity
import com.victorrubia.tfm.ui.theme.WearAppTheme
import javax.inject.Inject

/**
 * Activity that shows the user context menu
 */
class UserContextMenuActivity :  ComponentActivity() {

    @Inject
    lateinit var factory: UserContextMenuViewModelFactory
    private lateinit var userContextMenuViewModel: UserContextMenuViewModel

    // companion object to create the Intent to start this activity
    // and recovering the previously collected information.
    companion object{
        private const val STATUS_TILE = "statusTileSelected"
        fun intent(context: Context, statusTile: String)=
            Intent(context, UserContextMenuActivity::class.java).apply {
                putExtra(STATUS_TILE, statusTile)
            }
    }

    // State variables to store the previously collected information
    private val statusTilesSelected : String by lazy {
        intent?.getSerializableExtra(STATUS_TILE) as String
    }

    /**
     * Starts a new activity that shows the user context menu
     *
     * @param contextList the selected context list
     */
    fun startEmotions(contextList: ArrayList<String>){
        startActivity(EmotionsMenuActivity.intent(this, statusTilesSelected, contextList)).apply { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createUserContextMenuSubComponent()
            .inject(this)
        userContextMenuViewModel = ViewModelProvider(this, factory)
            .get(UserContextMenuViewModel::class.java)


        setContent{
            val navController = rememberNavController()
            NavHost(navController, startDestination = "announcement") {
                composable("announcement") { contextAnnouncement() }
                composable("contextList") {
                    ContextList({ selected ->
                    startEmotions(selected)
                    }, userContextMenuViewModel)
                }
            }
            userContextMenuViewModel.delayAnnouncement().observe(this){
                if(it != null && it){
                    navController.navigate("contextList")
                }
            }
        }
    }
}

/**
 * Composable function that shows the context register announcement
 */
@Composable
fun contextAnnouncement(){
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
                        imageVector = Icons.Rounded.EmojiPeople,
                        contentDescription = "Icono de persona",
                        modifier = Modifier.size(75.dp,75.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "AHORA REGISTRE\r\nCONTEXTO"
                )
            }
        }
    }
}

/**
 * Composable function that shows the context list menu.
 *
 * @param selectedItem function that navigates to emotions list and saves previously selected tags.
 */
@Composable
fun ContextList(selectedItem: (ArrayList<String>) -> Unit, viewModel: UserContextMenuViewModel){
    val selectedTilesNames = remember { ArrayList<String>() }
    val currentActivity by viewModel.getCurrentActivity().observeAsState(initial = null)
    val activitiesAssigned by viewModel.getActivitiesAssigned().observeAsState(initial = listOf())
    val tags = remember(currentActivity, activitiesAssigned) {
        activitiesAssigned.find { it.activity.id == (currentActivity?.activitiesRepositoryId) }
    }?.tags
    val selectedTiles = remember { tags?.let { List(it.size){ mutableStateOf(false) } } }
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
                        Text(text = "Registre contexto")
                    }
                }
                if (tags != null) {
                    for (tag in tags) {
                        if(tag.type == 2)
                            item { contextCards(tag.nameWearos, tag.name,
                                selectedTiles?.get(tags.indexOf(tag)) ?: mutableStateOf(false), selectedTilesNames) }
                    }
                }
                item { Spacer(Modifier.height(13.dp)) }
                item { finishedRegisteringContextChip(selectedItem, selectedTilesNames) }
            }
        }
    }
}

/**
 * Composable function that shows the cards for each context in the list.
 *
 * @param text text to be shown in the card.
 * @param icon icon to be shown in the card.
 * @param isSelected boolean that indicates if the card is selected or not.
 * @param selectedTileNames list of selected tiles.
 */
@Composable
fun contextCards(text : String, icon : String, isSelected : MutableState<Boolean>, selectedTileNames : ArrayList<String>){
    AppCard(
        appImage = {
            if(isSelected.value)
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
            isSelected.value = !isSelected.value
            if(isSelected.value){
                selectedTileNames.add(text)
            }
            else{
                selectedTileNames.remove(text)
            }
        },
        backgroundPainter = if (isSelected.value) ColorPainter(Color.DarkGray) else CardDefaults.cardBackgroundPainter(),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/tags/" + icon + ".png"))
                            .crossfade(true)
                            .placeholder(CircularProgressDrawable(LocalContext.current))
                            .build()
                    ),
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
 * Composable function that shows the button to finish the context selection.
 *
 * @param selectedItem function to be called when the button is pressed.
 * @param selectedTilesNames list of selected tiles.
 */
@Composable
fun finishedRegisteringContextChip(selectedItem: (ArrayList<String>) -> Unit, selectedTileNames: ArrayList<String>){
    Chip(
        onClick = { selectedItem(selectedTileNames) },
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