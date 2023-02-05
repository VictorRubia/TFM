package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.presentation.home.HomeActivity
import dagger.Subcomponent

/**
 * Dagger subcomponent for the home activity.
 */
@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    /**
     * Injects the home activity.
     *
     * @param homeActivity The activity to inject.
     */
    fun inject(homeActivity: HomeActivity)

    /**
     * Builder interface for creating an instance of [HomeSubComponent].
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : HomeSubComponent
    }
}