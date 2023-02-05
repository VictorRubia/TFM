package com.victorrubia.tfg.presentation.di.activity_confirmation

import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationActivity
import dagger.Subcomponent

/**
 * Dagger [Subcomponent] for [ActivityConfirmationActivity]
 */
@ActivityConfirmationScope
@Subcomponent(modules = [ActivityConfirmationModule::class])
interface ActivityConfirmationSubComponent {

    /**
     * Injects dependencies into the [ActivityConfirmationActivity]
     * @param activityConfirmationActivity [ActivityConfirmationActivity] to inject dependencies
     */
    fun inject(activityConfirmationActivity: ActivityConfirmationActivity)

    /**
     * Builder for [ActivityConfirmationSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : ActivityConfirmationSubComponent
    }
}