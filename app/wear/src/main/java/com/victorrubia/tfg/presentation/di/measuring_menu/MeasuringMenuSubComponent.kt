package com.victorrubia.tfg.presentation.di.measuring_menu

import com.victorrubia.tfg.presentation.measuring_menu.MeasuringMenuActivity
import dagger.Subcomponent

/**
 * Dagger subcomponent for the measuring menu activity.
 */
@MeasuringMenuScope
@Subcomponent(modules = [MeasuringMenuModule::class])
interface MeasuringMenuSubComponent {
    /**
     * Injects required dependencies into [MeasuringMenuActivity]
     * @param measuringMenuActivity [MeasuringMenuActivity] in which to inject dependencies
     */
    fun inject(measuringMenuActivity: MeasuringMenuActivity)

    /**
     * Builder for [MeasuringMenuSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : MeasuringMenuSubComponent
    }
}