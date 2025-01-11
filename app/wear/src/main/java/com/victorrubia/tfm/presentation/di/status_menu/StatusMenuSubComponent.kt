package com.victorrubia.tfm.presentation.di.status_menu

import com.victorrubia.tfm.presentation.status_menu.StatusMenuActivity
import dagger.Subcomponent

/**
 * Dagger [Subcomponent] for [StatusMenuActivity]
 */
@StatusMenuScope
@Subcomponent(modules = [StatusMenuModule::class])
interface StatusMenuSubComponent {

    /**
     * Injects dependencies into the [StatusMenuActivity]
     * @param activityTypeActivity [StatusMenuActivity] to inject dependencies
     */
    fun inject(activityTypeActivity: StatusMenuActivity)

    /**
     * Builder for [StatusMenuSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : StatusMenuSubComponent
    }
}