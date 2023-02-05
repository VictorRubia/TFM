package com.victorrubia.tfg.presentation.di.wear_service

import com.victorrubia.tfg.data.api.WearService
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [WearService]
 */
@WearServiceScope
@Subcomponent(modules = [WearServiceModule::class])
interface WearServiceSubComponent {
    /**
     * Injects required dependencies into [WearService]
     * @param wearService [WearService] to inject dependencies into
     */
    fun inject(wearService : WearService)

    /**
     * Builder for [WearServiceSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : WearServiceSubComponent
    }
}