package com.victorrubia.tfm.presentation.di.measuring_menu

import com.victorrubia.tfm.data.repository.step_measure.datasourceImpl.StepMeasureCacheDataSourceImpl
import com.victorrubia.tfm.domain.usecase.*
import com.victorrubia.tfm.presentation.measuring_menu.MeasuringMenuActivity
import com.victorrubia.tfm.presentation.measuring_menu.MeasuringMenuViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide the [MeasuringMenuActivity] dependencies.
 */
@Module
class MeasuringMenuModule {

    /**
     * Provides the [MeasuringMenuViewModelFactory] instance.
     *
     * @param getCurrentActivityUseCase The [GetCurrentActivityUseCase] instance.
     * @param savePPGMeasureUseCase The [SavePPGMeasureUseCase] instance.
     * @param endPPGMeasureUseCase The [EndPPGMeasureUseCase] instance.
     * @param endActivityUseCase The [EndActivityUseCase] instance.
     * @return The [MeasuringMenuViewModelFactory] instance.
     */
    @MeasuringMenuScope
    @Provides
    fun provideMeasuringMenuViewModelFactory(endActivityUseCase: EndActivityUseCase,
                                             getCurrentActivityUseCase: GetCurrentActivityUseCase,
                                             ) : MeasuringMenuViewModelFactory{
        return MeasuringMenuViewModelFactory(
            endActivityUseCase,
            getCurrentActivityUseCase,
        )
    }

}