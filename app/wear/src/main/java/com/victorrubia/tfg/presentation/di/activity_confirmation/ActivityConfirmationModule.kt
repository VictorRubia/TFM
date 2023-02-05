package com.victorrubia.tfg.presentation.di.activity_confirmation

import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
import com.victorrubia.tfg.presentation.activity_confirmation.ActivityConfirmationViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide ActivityConfirmationViewModelFactory.
 */
@Module
class ActivityConfirmationModule {

    /**
     * Provides ActivityConfirmationViewModelFactory.
     * @param newActivityUseCase The NewActivityUseCase to inject.
     * @return The ActivityConfirmationViewModelFactory.
     */
    @ActivityConfirmationScope
    @Provides
    fun provideActivityConfirmationViewModelFactory(newActivityUseCase: NewActivityUseCase) : ActivityConfirmationViewModelFactory {
        return ActivityConfirmationViewModelFactory(newActivityUseCase)
    }
}