package com.victorrubia.tfm.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfm.domain.usecase.*

/**
 * Factory for creating a [MeasuringMenuViewModel] with a constructor that takes a [EndActivityUseCase]
 * [EndPPGMeasureUseCase], [GetCurrentActivityUseCase] and [SavePPGMeasureUseCase].
 *
 * @property endActivityUseCase [EndActivityUseCase]
 * @property endPPGMeasureUseCase [EndPPGMeasureUseCase]
 * @property getCurrentActivityUseCase [GetCurrentActivityUseCase]
 * @property savePPGMeasureUseCase [SavePPGMeasureUseCase]
 */
class MeasuringMenuViewModelFactory(
    private val endActivityUseCase: EndActivityUseCase,
    private val getCurrentActivityUseCase: GetCurrentActivityUseCase,
) : ViewModelProvider.Factory {

    /**
     * Creates a [MeasuringMenuViewModel]
     *
     * @param modelClass [Class] of the [ViewModel]
     * @return [MeasuringMenuViewModel] instance
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeasuringMenuViewModel(
            endActivityUseCase,
            getCurrentActivityUseCase,
        ) as T
    }
}