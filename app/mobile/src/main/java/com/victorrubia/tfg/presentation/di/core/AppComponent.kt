package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent
import dagger.Component
import javax.inject.Singleton

/**
 * [AppComponent] interface is the core component of the application and all subcomponents will be
 * scoped to it.
 * Since all subcomponents depend on this component, it will be the entry point for all.
 * Singleton scope is used to ensure that all subcomponents created by this component are unique.
 * This component will be used by Dagger to create all subcomponents.
 */
@Singleton
@Component(modules = [
    AppModule::class,
    NetModule::class,
    DataBaseModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteDataModule::class,
    LocalDataModule::class,
    CacheDataModule::class,
])
interface AppComponent {

    /**
     * This will be used to create [HomeSubComponent].
     * @return [HomeSubComponent]
     */
    fun homeSubComponent(): HomeSubComponent.Factory
    /**
     * This will be used to create [LoggedSubComponent].
     * @return [LoggedSubComponent]
     */
    fun loggedSubComponent(): LoggedSubComponent.Factory

    /**
     * This will be used to create [EntrySubComponent]
     * @return [EntrySubComponent]
     */
    fun entrySubComponent() : EntrySubComponent.Factory

    /**
     * This will be used to create [RecoverPasswordSubComponent].
     * @return [RecoverPasswordSubComponent]
     */
    fun recoverPasswordSubComponent() : RecoverPasswordSubComponent.Factory

    /**
     * This will be used to create [WearServiceSubComponent].
     * @return [WearServiceSubComponent]
     */
    fun wearServiceSubComponent() : WearServiceSubComponent.Factory
}