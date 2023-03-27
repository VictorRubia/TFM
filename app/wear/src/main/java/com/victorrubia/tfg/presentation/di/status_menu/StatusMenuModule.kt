package com.victorrubia.tfg.presentation.di.status_menu

import com.victorrubia.tfg.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.presentation.status_menu.StatusMenuViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide StatusMenuViewModelFactory.
 */
@Module
class StatusMenuModule {

    /**
     * Provides StatusMenuViewModelFactory.
     * @param newActivityUseCase The NewActivityUseCase to inject.
     * @return The StatusMenuViewModelFactory.
     */
    @StatusMenuScope
    @Provides
    fun provideStatusMenuViewModelFactory(getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
                                          getCurrentActivityUseCase: GetCurrentActivityUseCase) : StatusMenuViewModelFactory {
        return StatusMenuViewModelFactory(getActivitiesAssignedUseCase, getCurrentActivityUseCase)
    }
}