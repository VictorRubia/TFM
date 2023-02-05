package com.victorrubia.tfg.presentation.di.logged

import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfg.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfg.domain.usecase.SendApiKeyToWearUseCase
import com.victorrubia.tfg.presentation.logged.LoggedViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Dagger module to provide dependencies for the [LoggedViewModelFactory].
 */
@Module
class LoggedModule {

    /**
     * Provides the [LoggedViewModelFactory] instance.
     * @param getUserUseCase The [GetUserUseCase] implementation.
     * @param isWearConnectedUseCase The [IsWearConnectedUseCase] implementation.
     * @param removeLocalUserUseCase The [RemoveLocalUserUseCase] implementation.
     * @param sendApiKeyToWearUseCase The [SendApiKeyToWearUseCase] implementation.
     * @return The [LoggedViewModelFactory] instance.
     */
    @LoggedScope
    @Provides
    fun provideLoggedViewModelFactory(
        getUserUseCase: GetUserUseCase,
        removeLocalUserUseCase: RemoveLocalUserUseCase,
        sendApiKeyToWearUseCase: SendApiKeyToWearUseCase,
        isWearConnectedUseCase: IsWearConnectedUseCase
    ): LoggedViewModelFactory{
        return LoggedViewModelFactory(
            getUserUseCase,
            removeLocalUserUseCase,
            sendApiKeyToWearUseCase,
            isWearConnectedUseCase
        )
    }
}