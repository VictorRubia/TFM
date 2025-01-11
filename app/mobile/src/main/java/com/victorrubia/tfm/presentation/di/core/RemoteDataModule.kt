package com.victorrubia.tfm.presentation.di.core

import android.content.Context
import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfm.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides remote data sources dependencies.
 */
@Module
class RemoteDataModule {

    /**
     * Provides user remote data source.
     *
     * @param context the context
     * @return [UserRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(tfmService : TFMService, context: Context) : UserRemoteDatasource {
        return UserRemoteDataSourceImpl(tfmService, context)
    }

}