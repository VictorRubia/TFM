package com.victorrubia.tfg.presentation.di.logged

import com.victorrubia.tfg.presentation.logged.LoggedActivity
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [LoggedActivity]
 */
@LoggedScope
@Subcomponent(modules = [LoggedModule::class])
interface LoggedSubComponent {
    /**
     * Injects required dependencies into [LoggedActivity]
     * @param loggedActivity [LoggedActivity] in which to inject dependencies
     */
    fun inject(loggedActivity: LoggedActivity)

    /**
     * Builder for [LoggedSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : LoggedSubComponent
    }
}