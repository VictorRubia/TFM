package com.victorrubia.tfg.presentation.di.user_context_menu

import com.victorrubia.tfg.presentation.user_context_menu.UserContextMenuActivity
import dagger.Subcomponent

/**
 * Dagger [Subcomponent] for [UserContextMenuActivity]
 */
@UserContextMenuScope
@Subcomponent(modules = [UserContextMenuModule::class])
interface UserContextMenuSubComponent {

    /**
     * Injects dependencies into the [UserContextMenuActivity]
     * @param activityTypeActivity [UserContextMenuActivity] to inject dependencies
     */
    fun inject(activityTypeActivity: UserContextMenuActivity)

    /**
     * Builder for [UserContextMenuSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : UserContextMenuSubComponent
    }
}