package com.victorrubia.tfg.presentation.di.activity_type

import com.victorrubia.tfg.presentation.activity_type.ActivityTypeActivity
import dagger.Subcomponent

/**
 * Dagger [Subcomponent] for [ActivityTypeActivity]
 */
@ActivityTypeScope
@Subcomponent(modules = [ActivityTypeModule::class])
interface ActivityTypeSubComponent {

    /**
     * Injects dependencies into the [ActivityTypeActivity]
     * @param activityTypeActivity [ActivityTypeActivity] to inject dependencies
     */
    fun inject(activityTypeActivity: ActivityTypeActivity)

    /**
     * Builder for [ActivityTypeSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : ActivityTypeSubComponent
    }
}