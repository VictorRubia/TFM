package com.victorrubia.tfg.presentation.di.user_context_menu

import com.victorrubia.tfg.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.presentation.user_context_menu.UserContextMenuViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide UserContextMenuViewModelFactory.
 */
@Module
class UserContextMenuModule {

    /**
     * Provides UserContextMenuViewModelFactory.
     * @param newActivityUseCase The NewActivityUseCase to inject.
     * @return The UserContextMenuViewModelFactory.
     */
    @UserContextMenuScope
    @Provides
    fun provideUserContextMenuViewModelFactory(getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase,
                                          getCurrentActivityUseCase: GetCurrentActivityUseCase) : UserContextMenuViewModelFactory {
        return UserContextMenuViewModelFactory(getActivitiesAssignedUseCase, getCurrentActivityUseCase)
    }
}