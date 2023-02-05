package com.victorrubia.tfg.presentation

import android.app.Application
import com.victorrubia.tfg.BuildConfig
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.core.*
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent

/**
 * Application class
 */
class App : Application(), Injector {

    // Application components
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        // Initialize application components
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
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
     * Builds a new [ActivityConfirmationSubComponent]
     *
     * @return [ActivityConfirmationSubComponent]
     */
    override fun createActivityConfirmationSubComponent(): ActivityConfirmationSubComponent {
        return appComponent.activityConfirmationSubComponent().create()
    }

    /**
     * Builds a new [MeasuringMenuSubComponent]
     *
     * @return [MeasuringMenuSubComponent]
     */
    override fun createMeasuringMenuSubComponent(): MeasuringMenuSubComponent {
        return appComponent.measuringMenuSubComponent().create()
    }

    /**
     * Builds a new [FeelingsMenuSubComponent]
     *
     * @return [FeelingsMenuSubComponent]
     */
    override fun createFeelingsMenuSubComponent(): FeelingsMenuSubComponent {
        return appComponent.feelingsMenuSubComponent().create()
    }
}