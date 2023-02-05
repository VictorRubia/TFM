package com.victorrubia.tfg.presentation.measuring_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.domain.usecase.*

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
    private val savePPGMeasureUseCase: SavePPGMeasureUseCase,
    private val saveAccelerometerMeasureUseCase: SaveAccelerometerMeasureUseCase,
    private val saveGPSMeasureUseCase: SaveGPSMeasureUseCase,
    private val saveStepMeasureUseCase: SaveStepMeasureUseCase,
    private val saveSignificantMovMeasureUseCase: SaveSignificantMovMeasureUseCase,
    private val endPPGMeasureUseCase: EndPPGMeasureUseCase,
    private val endAccelerometerMeasureUseCase: EndAccelerometerMeasureUseCase,
    private val endGPSMeasureUseCase: EndGPSMeasureUseCase,
    private val endStepMeasureUseCase: EndStepMeasureUseCase,
    private val endSignificantMovMeasureUseCase: EndSignificantMovMeasureUseCase
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
            savePPGMeasureUseCase,
            saveAccelerometerMeasureUseCase,
            saveGPSMeasureUseCase,
            saveStepMeasureUseCase,
            saveSignificantMovMeasureUseCase,
            endPPGMeasureUseCase,
            endAccelerometerMeasureUseCase,
            endGPSMeasureUseCase,
            endStepMeasureUseCase,
            endSignificantMovMeasureUseCase,
        ) as T
    }
}