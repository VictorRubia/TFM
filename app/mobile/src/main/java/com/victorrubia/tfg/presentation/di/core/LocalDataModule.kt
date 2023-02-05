package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides local data sources dependencies.
 */
@Module
class LocalDataModule {

    /**
     * Provides the local data source for the user repository.
     *
     * @param userDao The user DAO.
     * @return The local data source [UserLocalDataSource].
     */
    @Singleton
    @Provides
    fun provideUserLocalDataSource(userDao: UserDao) : UserLocalDataSource {
        return UserLocalDataSourceImpl(userDao)
    }

}