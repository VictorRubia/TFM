package com.victorrubia.tfm.presentation.di.core

import com.victorrubia.tfm.data.api.TFMService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Dagger module that provides the TFM API service.
 */
@Module
class NetModule(private val baseUrl : String) {

    /**
     * Provides the TFM API service implementation
     * using retrofit to handle the HTTP calls.
     *
     * @return the TFM service implementation.
     */
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    /**
     * Provides the TFM service implementation.
     *
     * @param retrofit the retrofit instance to initialize the service.
     * @return the TFM service implementation.
     */
    @Singleton
    @Provides
    fun provideTFMService(retrofit: Retrofit) : TFMService{
        return retrofit.create(TFMService::class.java)
    }

}