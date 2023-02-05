package com.victorrubia.tfg.presentation.di.recover_password

import com.victorrubia.tfg.domain.usecase.RecoverPasswordUseCase
import com.victorrubia.tfg.presentation.recover_password.RecoverPasswordViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [RecoverPasswordModule].
 */
@Module
class RecoverPasswordModule {

    /**
     * Provides the [RecoverPasswordViewModelFactory] instance.
     * @param recoverPasswordUseCase The [RecoverPasswordUseCase] implementation.
     * @return The [RecoverPasswordViewModelFactory] instance.
     */
    @RecoverPasswordScope
    @Provides
    fun provideRecoverPasswordViewModelFactory(
        recoverPasswordUseCase: RecoverPasswordUseCase
    ): RecoverPasswordViewModelFactory{
        return RecoverPasswordViewModelFactory(
            recoverPasswordUseCase
        )
    }
}