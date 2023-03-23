package com.victorrubia.tfg.presentation.di.activity_type

import com.victorrubia.tfg.domain.usecase.GetActivitiesAssignedUseCase
import com.victorrubia.tfg.presentation.activity_type.ActivityTypeViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide ActivityTypeViewModelFactory.
 */
@Module
class ActivityTypeModule {

    /**
     * Provides ActivityTypeViewModelFactory.
     * @param newActivityUseCase The NewActivityUseCase to inject.
     * @return The ActivityTypeViewModelFactory.
     */
    @ActivityTypeScope
    @Provides
    fun provideActivityTypeViewModelFactory(getActivitiesAssignedUseCase: GetActivitiesAssignedUseCase) : ActivityTypeViewModelFactory {
        return ActivityTypeViewModelFactory(getActivitiesAssignedUseCase)
    }
}