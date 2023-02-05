package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
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
    fun homeSubComponent() : HomeSubComponent.Factory

    /**
     * This will be used to create [ActivityConfirmationSubComponent].
     * @return [ActivityConfirmationSubComponent]
     */
    fun activityConfirmationSubComponent() : ActivityConfirmationSubComponent.Factory

    /**
     * This will be used to create [MeasuringMenuSubComponent].
     * @return [MeasuringMenuSubComponent]
     */
    fun measuringMenuSubComponent() : MeasuringMenuSubComponent.Factory

    /**
     * This will be used to create [FeelingsMenuSubComponent].
     * @return [FeelingsMenuSubComponent]
     */
    fun feelingsMenuSubComponent() : FeelingsMenuSubComponent.Factory

}