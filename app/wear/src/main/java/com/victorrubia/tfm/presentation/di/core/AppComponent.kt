package com.victorrubia.tfm.presentation.di.core

import com.victorrubia.tfm.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfm.presentation.di.activity_type.ActivityTypeSubComponent
import com.victorrubia.tfm.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfm.presentation.di.home.HomeSubComponent
import com.victorrubia.tfm.presentation.di.measuring_menu.MeasuringMenuSubComponent
import com.victorrubia.tfm.presentation.di.measuring_service.MeasuringServiceSubComponent
import com.victorrubia.tfm.presentation.di.status_menu.StatusMenuSubComponent
import com.victorrubia.tfm.presentation.di.user_context_menu.UserContextMenuSubComponent
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

    fun measuringServiceSubComponent() : MeasuringServiceSubComponent.Factory

    /**
     * This will be used to create [FeelingsMenuSubComponent].
     * @return [FeelingsMenuSubComponent]
     */
    fun feelingsMenuSubComponent() : FeelingsMenuSubComponent.Factory

    fun activityTypeSubComponent() : ActivityTypeSubComponent.Factory

    fun statusMenuSubComponent() : StatusMenuSubComponent.Factory

    fun userContextMenuSubComponent() : UserContextMenuSubComponent.Factory

}