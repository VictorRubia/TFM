package com.victorrubia.tfm.presentation.di.recover_password

import com.victorrubia.tfm.domain.usecase.RecoverPasswordUseCase
import com.victorrubia.tfm.presentation.recover_password.RecoverPasswordViewModelFactory
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