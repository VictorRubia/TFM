package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.domain.repository.UserRepository
import com.victorrubia.tfg.domain.usecase.*
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides use cases.
 */
@Module
class UseCaseModule {

    /**
     * Provides the [GetUserUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [GetUserUseCase] use case.
     */
    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    /**
     * Provides the [RemoveLocalUserUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [RemoveLocalUserUseCase] use case.
     */
    @Provides
    fun provideRemoveLocalUserUseCase(userRepository: UserRepository) : RemoveLocalUserUseCase{
        return RemoveLocalUserUseCase(userRepository)
    }

    /**
     * Provides the [RecoverPasswordUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [RecoverPasswordUseCase] use case.
     */
    @Provides
    fun provideRecoverPasswordUseCase(userRepository: UserRepository) : RecoverPasswordUseCase{
        return RecoverPasswordUseCase(userRepository)
    }

    /**
     * Provides the [SendApiKeyToWearUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [SendApiKeyToWearUseCase] use case.
     */
    @Provides
    fun provideSendApiKeyToWeardUseCase(userRepository: UserRepository) : SendApiKeyToWearUseCase{
        return SendApiKeyToWearUseCase(userRepository)
    }

    /**
     * Provides the [IsWearConnectedUseCase] use case.
     *
     * @param userRepository The repository where the users are stored.
     * @return The [IsWearConnectedUseCase] use case.
     */
    @Provides
    fun provideIsWearConnecteddUseCase(userRepository: UserRepository) : IsWearConnectedUseCase {
        return IsWearConnectedUseCase(userRepository)
    }

}