package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.presentation.home.HomeActivity
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [HomeActivity]
 */
@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    /**
     * Injects required dependencies into [HomeActivity]
     * @param homeActivity [HomeActivity] in which to inject dependencies
     */
    fun inject(homeActivity: HomeActivity)

    /**
     * Builder for [HomeSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : HomeSubComponent
    }
}