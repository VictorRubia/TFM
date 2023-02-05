package com.victorrubia.tfg.presentation.di.recover_password

import com.victorrubia.tfg.presentation.recover_password.RecoverPasswordActivity
import dagger.Subcomponent

/**
 * [Subcomponent] that injects required dependencies into [RecoverPasswordActivity]
 */
@RecoverPasswordScope
@Subcomponent(modules = [RecoverPasswordModule::class])
interface RecoverPasswordSubComponent {
    /**
     * Injects required dependencies into [RecoverPasswordActivity]
     * @param recoverPasswordActivity [RecoverPasswordActivity] in which to inject the dependencies
     */
    fun inject(recoverPasswordActivity: RecoverPasswordActivity)

    /**
     * Builder for [RecoverPasswordSubComponent]
     */
    @Subcomponent.Factory
    interface Factory{
        fun create() : RecoverPasswordSubComponent
    }
}