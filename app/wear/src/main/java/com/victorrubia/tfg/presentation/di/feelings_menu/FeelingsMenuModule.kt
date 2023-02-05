package com.victorrubia.tfg.presentation.di.feelings_menu

import com.victorrubia.tfg.domain.usecase.AddTagUseCase
import com.victorrubia.tfg.domain.usecase.GetCurrentActivityUseCase
import com.victorrubia.tfg.presentation.feelings_menu.FeelingsMenuViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [FeelingsMenuViewModelFactory].
 */
@Module
class FeelingsMenuModule {

    /**
     * Provides the [FeelingsMenuViewModelFactory] instance.
     * @param getCurrentActivityUseCase The [GetCurrentActivityUseCase] instance.
     * @param addTagUseCase The [AddTagUseCase] instance.
     * @return The [FeelingsMenuViewModelFactory] instance.
     */
    @FeelingsMenuScope
    @Provides
    fun provideFeelingsMenuViewModelFactory(getCurrentActivityUseCase: GetCurrentActivityUseCase,
                                            addTagUseCase: AddTagUseCase) : FeelingsMenuViewModelFactory{
        return FeelingsMenuViewModelFactory(getCurrentActivityUseCase, addTagUseCase)
    }

}