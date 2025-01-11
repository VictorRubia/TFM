package com.victorrubia.tfm.presentation.di.measuring_service

import com.victorrubia.tfm.presentation.measuring_menu.MeasuringMenuActivity
import com.victorrubia.tfm.presentation.measuring_menu.MeasuringService
import dagger.Subcomponent

/**
 * Dagger subcomponent for the measuring menu activity.
 */
@MeasuringServiceScope
@Subcomponent
interface MeasuringServiceSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MeasuringServiceSubComponent
    }

    fun inject(measuringService: MeasuringService)
}