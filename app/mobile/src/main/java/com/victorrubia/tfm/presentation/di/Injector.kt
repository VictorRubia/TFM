package com.victorrubia.tfm.presentation.di

import com.victorrubia.tfm.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfm.presentation.di.home.HomeSubComponent
import com.victorrubia.tfm.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfm.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfm.presentation.di.wear_service.WearServiceSubComponent

/**
 * Injector class that holds all the subcomponents
 */
interface Injector {

    /**
     * Creates the [EntrySubComponent]
     *
     * @return [EntrySubComponent]
     */
    fun createEntrySubComponent(): EntrySubComponent
    /**
     * Creates the [HomeSubComponent].
     *
     * @return the [HomeSubComponent].
     */
    fun createHomeSubComponent(): HomeSubComponent
    /**
     * Creates the [LoggedSubComponent].
     *
     * @return the [LoggedSubComponent].
     */
    fun createLoggedSubComponent(): LoggedSubComponent
    /**
     * Creates the [RecoverPasswordSubComponent].
     *
     * @return the [RecoverPasswordSubComponent].
     */
    fun createRecoverPasswordSubComponent(): RecoverPasswordSubComponent
    /**
     * Creates the [WearServiceSubComponent].
     *
     * @return the [WearServiceSubComponent].
     */
    fun createWearServiceSubComponent(): WearServiceSubComponent
}