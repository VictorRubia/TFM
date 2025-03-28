package com.victorrubia.tfm.presentation.di.wear_service

import com.victorrubia.tfm.data.api.WearService
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [WearService].
 */
@Module
class WearServiceModule {
    /**
     * Provide the [WearService] instance.
     * @return the [WearService] instance.
     */
    @WearServiceScope
    @Provides
    fun provideWearService(): WearService{
        return WearService()
    }
}