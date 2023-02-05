package com.victorrubia.tfg.presentation.di.feelings_menu

import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuActivity
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [FeelingsMenuActivity]
 */
@FeelingsMenuScope
@Subcomponent(modules = [FeelingsMenuModule::class])
interface FeelingsMenuSubComponent {
    /**
     * Injects required dependencies into [FeelingsMenuActivity]
     * @param feelingsMenuActivity [FeelingsMenuActivity] in which to inject dependencies
     */
    fun inject(feelingsMenuActivity: FeelingsMenuActivity)

    /**
     * Builder for [FeelingsMenuSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : FeelingsMenuSubComponent
    }

}