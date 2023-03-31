package com.victorrubia.tfg.presentation.status_menu

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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.victorrubia.tfg.data.model.tag_repository.TagRepository
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.user_context_menu.UserContextMenuActivity
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

/**
 * Activity that shows the status list menu
 */
class StatusMenuActivity :  ComponentActivity() {

    @Inject
    lateinit var factory: StatusMenuViewModelFactory
    private lateinit var statusMenuViewModel: StatusMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createStatusMenuSubComponent()
            .inject(this)
        statusMenuViewModel = ViewModelProvider(this, factory)
            .get(StatusMenuViewModel::class.java)

        setContent{
            StatusList({selected ->
                startActivity(UserContextMenuActivity.intent(this,selected)).apply { finish() }
            }, statusMenuViewModel)
        }
    }
}

/**
 * Composable function that shows the status list menu
 *
 * @param selectedItem function that navigates to contexts menu activity [UserContextMenuActivity] and saves the elected status
 */
@Composable
fun StatusList(selectedItem: (String) -> Unit, viewModel : StatusMenuViewModel){
    val selectedTilesNames = remember { mutableStateOf("")  }
    val currentActivity by viewModel.getCurrentActivity().observeAsState(initial = null)
    val activitiesAssigned by viewModel.getActivitiesAssigned().observeAsState(initial = listOf())
    val tags = remember(currentActivity, activitiesAssigned) {
        activitiesAssigned.find { it.activity.id == (currentActivity?.activitiesRepositoryId) }
    }?.tags

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
            // You can display a loading indicator while waiting for the data
            if (currentActivity == null || activitiesAssigned.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            else {
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
                    if (tags != null) {
                        for (tag in tags) {
                            if(tag.type == 1)
                                item { statusCards(tag, selectedTilesNames) }
                        }
                    }
                    item { Spacer(Modifier.height(13.dp)) }
                    item { finishedRegisteringStatusChip(selectedItem, selectedTilesNames) }
                }
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
fun statusCards(tag : TagRepository, selectedTileName : MutableState<String>){
    AppCard(
        appImage = {
            if(selectedTileName.value.contains(tag.nameWearos))
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
            if(selectedTileName.value != tag.nameWearos){
                selectedTileName.value = tag.nameWearos
            }
            else{
                selectedTileName.value = ""
            }
                  },
        backgroundPainter = if (selectedTileName.value == tag.nameWearos) ColorPainter(Color.DarkGray) else CardDefaults.cardBackgroundPainter(),
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/tags/" + tag.name + ".png"))
                            .crossfade(true)
                            .placeholder(CircularProgressDrawable(LocalContext.current))
                            .build()
                    ),
                    contentDescription = tag.nameWearos,
                    modifier = Modifier.size(width = 100.dp, height = 100.dp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = tag.nameWearos.uppercase()
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