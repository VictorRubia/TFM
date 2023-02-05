package com.victorrubia.tfg.presentation.di.entry

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.presentation.entry.EntryViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [EntryViewModelFactory].
 */
@Module
class EntryModule {

    /**
     * Provides the [EntryViewModelFactory] instance.
     *
     * @param getUserUseCase the [GetUserUseCase] instance.
     * @return the [GetUserUseCase] instance.
     */
    @EntryScope
    @Provides
    fun provideEntryViewModelFactory(
        getUserUseCase: GetUserUseCase
    ): EntryViewModelFactory{
        return EntryViewModelFactory(
            getUserUseCase
        )
    }
}