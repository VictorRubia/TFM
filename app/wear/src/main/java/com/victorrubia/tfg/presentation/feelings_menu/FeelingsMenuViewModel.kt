package com.victorrubia.tfg.presentation.feelings_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.victorrubia.tfg.data.model.status.Status
import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

/**
 * [ViewModel] for [FeelingsMenuActivity]
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase]
 * @property addTagUseCase [AddTagUseCase]
 */
class FeelingsMenuViewModel(
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
    private val addTagUseCase: AddTagUseCase,
) : ViewModel() {

    /**
     * Add Tag use case execution
     *
     * @param statusTags [Status] tags
     * @param contextTags [Context] tags
     * @param emotionsTags [Emotions] tags
     * @param feelingsTags [Feelings] tags
     */
    fun addTag(statusTags : String, contextTags : ArrayList<String>, emotionsTags : String, feelingsTags : String){
        CoroutineScope(Dispatchers.IO).launch {
            getCurrentActivityUseCase.execute()?.let { addTagUseCase.execute(Json.encodeToString(Status(statusTags, contextTags, emotionsTags, feelingsTags)), Date(), it.id) }
        }
    }

    /**
     * Function to show the announcement for a certain time
     */
    fun delayAnnouncement() = liveData<Boolean> {
        delay(2500)
        emit(true)
    }
}