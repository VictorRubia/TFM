package com.victorrubia.tfg.presentation.di.home

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.domain.usecase.RequestUserUseCase
import com.victorrubia.tfg.domain.usecase.SaveUserUseCase
import com.victorrubia.tfg.presentation.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides the [HomeViewModelFactory] and its dependencies.
 */
@Module
class HomeModule {

    /**
     * Provides the [HomeViewModelFactory] instance.
     *
     * @param requestUserUseCase The [RequestUserUseCase] to be provided.
     * @param getUserUseCase The [GetUserUseCase] to be provided.
     * @param saveUserUseCase The [SaveUserUseCase] to be provided.
     * @param newActivityUseCase The [NewActivityUseCase] to be provided.
     * @return The [HomeViewModelFactory] to be provided.
     */
    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(requestUserUseCase: RequestUserUseCase,
                                    saveUserUseCase: SaveUserUseCase) : HomeViewModelFactory{
        return HomeViewModelFactory(requestUserUseCase, saveUserUseCase)
    }
}