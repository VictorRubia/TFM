package com.victorrubia.tfm.presentation

import android.app.Application
import com.victorrubia.tfm.BuildConfig
import com.victorrubia.tfm.presentation.di.Injector
import com.victorrubia.tfm.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfm.presentation.di.activity_type.ActivityTypeSubComponent
import com.victorrubia.tfm.presentation.di.core.*
import com.victorrubia.tfm.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfm.presentation.di.home.HomeSubComponent
import com.victorrubia.tfm.presentation.di.measuring_menu.MeasuringMenuSubComponent
import com.victorrubia.tfm.presentation.di.measuring_service.MeasuringServiceSubComponent
import com.victorrubia.tfm.presentation.di.status_menu.StatusMenuSubComponent
import com.victorrubia.tfm.presentation.di.user_context_menu.UserContextMenuSubComponent

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

    override fun createMeasuringServiceSubComponent(): MeasuringServiceSubComponent {
        return appComponent.measuringServiceSubComponent().create()
    }

    /**
     * Builds a new [FeelingsMenuSubComponent]
     *
     * @return [FeelingsMenuSubComponent]
     */
    override fun createFeelingsMenuSubComponent(): FeelingsMenuSubComponent {
        return appComponent.feelingsMenuSubComponent().create()
    }

    override fun createActivityTypeSubComponent(): ActivityTypeSubComponent {
        return appComponent.activityTypeSubComponent().create()
    }

    override fun createStatusMenuSubComponent(): StatusMenuSubComponent {
        return appComponent.statusMenuSubComponent().create()
    }

    override fun createUserContextMenuSubComponent(): UserContextMenuSubComponent {
        return appComponent.userContextMenuSubComponent().create()
    }
}