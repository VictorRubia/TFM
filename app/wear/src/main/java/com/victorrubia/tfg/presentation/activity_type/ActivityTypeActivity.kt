package com.victorrubia.tfg.presentation.activity_type

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.wear.compose.material.*
import coil.Coil
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.model.activity_repository.ActivityRepository
import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationActivity
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.ui.theme.WearAppTheme
import javax.inject.Inject

/**
 * ActivityTypeActivity
 *
 * Activity that shows the different types of activities that can be created
 */
class ActivityTypeActivity :  ComponentActivity() {
    @Inject
    lateinit var factory: ActivityTypeViewModelFactory
    private lateinit var activityTypeViewModel: ActivityTypeViewModel
    private var activitiesAssigned : List<ActivityAssignation> = listOf()
    private var isLoadingActivities = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        (application as Injector).createActivityTypeSubComponent()
            .inject(this)
        activityTypeViewModel = ViewModelProvider(this, factory)
            .get(ActivityTypeViewModel::class.java)

        activityTypeViewModel.getActivitiesAssigned().observe(this) {
            activitiesAssigned = it
            Log.d("ACTIVITIES_ASSIGNED", activitiesAssigned.toString())
            isLoadingActivities.value = false
            setContent {
                ActivityTypeList({
                    startActivity(ActivityConfirmationActivity.intent(this,it))
                    finish()
                },activitiesAssigned, isLoadingActivities)
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
fun ActivityTypeList(createActivity: (Int) -> Unit, activitiesAssigned : List<ActivityAssignation>, isLoadingActivities: MutableState<Boolean>){
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
            if (isLoadingActivities.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator()
                }
            } else {
                val context = LocalContext.current
                LaunchedEffect(activitiesAssigned) {
                    val imageLoader = Coil.imageLoader(context)

                    activitiesAssigned.forEach { activity ->
                        val request = ImageRequest.Builder(context)
                            .data(BuildConfig.BASE_URL.split("/api")[0] + activity.activity.iconUrl)
                            .build()

                        imageLoader.enqueue(request)
                    }
                }
                ScalingLazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    anchorType = ScalingLazyListAnchorType.ItemStart,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(state = listState),
                    state = listState
                ) {
                    for (activity in activitiesAssigned) {
                        item { activityTypeChip(activity.activity, loading, createActivity) }
                    }
                }
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
fun activityTypeChip(activity : ActivityRepository, isSelected : MutableState<Boolean>, createActivity: (Int) -> Unit){
    Chip(
        onClick = { createActivity(activity.id)
                isSelected.value = false},
        enabled = isSelected.value,
        label = {
            Text(
                text = activity.nameWearos,
            )
        },
        icon = {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = BuildConfig.BASE_URL.split("/api")[0] + activity.iconUrl)
                    .crossfade(true)
                    .placeholder(CircularProgressDrawable(LocalContext.current))
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "Icono de la actividad",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
                contentScale = ContentScale.Fit
            )
        },
    )
}