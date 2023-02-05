package com.victorrubia.tfg.presentation

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.di.core.*
import com.victorrubia.tfg.presentation.di.entry.EntrySubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.logged.LoggedSubComponent
import com.victorrubia.tfg.presentation.di.recover_password.RecoverPasswordSubComponent
import com.victorrubia.tfg.presentation.di.wear_service.WearServiceSubComponent

/**
 * Application class
 */
class App : Application(), Injector {

    // Application components
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        // Material Design 3
        DynamicColors.applyToActivitiesIfAvailable(this)

        // Initialize application components
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .cacheDataModule(CacheDataModule())
            .localDataModule(LocalDataModule())
            .build()
    }

    /**
     * Builds a new [HomeSubComponent]
     *
     * @return [HomeSubComponent]
     */
    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }

    /**
     * Builds a new [LoggedSubComponent]
     *
     * @return [LoggedSubComponent]
     */
    override fun createLoggedSubComponent(): LoggedSubComponent{
        return appComponent.loggedSubComponent().create()
    }

    /**
     * Builds a new [EntrySubComponent]
     *
     * @return [EntrySubComponent]
     */
    override fun createEntrySubComponent(): EntrySubComponent {
        return appComponent.entrySubComponent().create()
    }

    /**
     * Builds a new [RecoverPasswordSubComponent]
     *
     * @return [RecoverPasswordSubComponent]
     */
    override fun createRecoverPasswordSubComponent(): RecoverPasswordSubComponent {
        return appComponent.recoverPasswordSubComponent().create()
    }

    /**
     * Builds a new [WearServiceSubComponent]
     *
     * @return [WearServiceSubComponent]
     */
    override fun createWearServiceSubComponent(): WearServiceSubComponent {
        return appComponent.wearServiceSubComponent().create()
    }
}