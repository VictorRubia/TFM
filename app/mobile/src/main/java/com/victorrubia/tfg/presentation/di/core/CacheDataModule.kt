package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides the cache data sources.
 */
@Module
class CacheDataModule {

    /**
     * Provides the [UserCacheDataSource].
     * Singleton annotation is used to make sure that there is only one instance of the data source.
     *
     * @param UserCacheDataSourceImpl the implementation of the [UserCacheDataSource]
     * @return the [UserCacheDataSource]
     */
    @Singleton
    @Provides
    fun provideUserCacheDataSource() : UserCacheDataSource {
        return UserCacheDataSourceImpl()
    }
}