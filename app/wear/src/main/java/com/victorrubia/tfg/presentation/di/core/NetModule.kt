package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.api.TFGService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module that provides the TFG API service.
 */
@Module
class NetModule(private val baseUrl : String) {

    /**
     * Provides the TFG API service implementation
     * using retrofit to handle the HTTP calls.
     *
     * @return the TFG service implementation.
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
     * Provides the TFG service implementation.
     *
     * @param retrofit the retrofit instance to initialize the service.
     * @return the TFG service implementation.
     */
    @Singleton
    @Provides
    fun provideTFGService(retrofit: Retrofit) : TFGService{
        return retrofit.create(TFGService::class.java)
    }

}