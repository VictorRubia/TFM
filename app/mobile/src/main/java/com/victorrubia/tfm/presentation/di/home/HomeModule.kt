package com.victorrubia.tfm.presentation.di.home

import com.victorrubia.tfm.domain.usecase.GetUserUseCase
import com.victorrubia.tfm.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [HomeViewModelFactory].
 */
@Module
class HomeModule {

    /**
     * Provides the [HomeViewModelFactory] instance.
     * @param getUserUseCase The [GetUserUseCase] instance.
     * @return the [HomeViewModelFactory] instance.
     */
    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(getUserUseCase: GetUserUseCase): HomeViewModelFactory {
        return HomeViewModelFactory(getUserUseCase)
    }
}