package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.presentation.di.activity_confirmation.ActivityConfirmationSubComponent
import com.victorrubia.tfg.presentation.di.feelings_menu.FeelingsMenuSubComponent
import com.victorrubia.tfg.presentation.di.home.HomeSubComponent
import com.victorrubia.tfg.presentation.di.measuring_menu.MeasuringMenuSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger [AppModule] that provides objects which will live during the application lifecycle.
 */
@Module(subcomponents = [
    HomeSubComponent::class,
    ActivityConfirmationSubComponent::class,
    MeasuringMenuSubComponent::class,
    FeelingsMenuSubComponent::class,
])
class AppModule(private val context : Context) {

    /**
     * Provides the application [Context].
     * @return the application [Context].
     * Singleton annotation ensures that the object is created only once.
     */
    @Singleton
    @Provides
    fun provideApplicationContext() : Context {
        return context.applicationContext
    }

}