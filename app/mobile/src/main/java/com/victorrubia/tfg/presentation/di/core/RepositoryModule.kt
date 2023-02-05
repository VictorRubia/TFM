package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfg.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides repository dependencies.
 */
@Module
class RepositoryModule {

    /**
     * Provides the [UserRepository] implementation.
     *
     * @param userRemoteDataSource the [UserRemoteDataSource] implementation.
     * @param userLocalDataSource the [UserLocalDataSource] implementation.
     * @param userCacheDataSource the [UserCacheDataSource] implementation.
     * @return the [UserRepository] implementation.
     */
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDatasource: UserRemoteDatasource,
        userLocalDataSource: UserLocalDataSource,
        userCacheDataSource: UserCacheDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDatasource,
            userLocalDataSource,
            userCacheDataSource,
        )
    }
}