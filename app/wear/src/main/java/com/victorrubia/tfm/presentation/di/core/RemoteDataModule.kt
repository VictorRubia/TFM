package com.victorrubia.tfm.presentation.di.core

import android.content.Context
import com.victorrubia.tfm.data.api.TFMService
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasourceImpl.AccelerometerMeasureRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfm.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfm.data.repository.activity_assignation.datasourceImpl.ActivityAssignationRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasourceImpl.GPSMeasureRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.ppg_measure.datasourceImpl.PPGMeasureRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.significantMov_measure.datasourceImpl.SignificantMovMeasureRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.step_measure.datasourceImpl.StepMeasureRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfm.data.repository.tag.datasourceImpl.TagRemoteDataSourceImpl
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserRemoteDataSource
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
     * Provides activity remote data source.
     *
     * @param tfmService the TFM service
     * @param userDataSource the user data source
     * @return [ActivityRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfmService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideActivityAssignationRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource, context: Context) : ActivityAssignationRemoteDataSource {
        return ActivityAssignationRemoteDataSourceImpl(tfmService, userDataSource, context)
    }

    /**
     * Provides user remote data source.
     *
     * @param context the context
     * @return [UserRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(context: Context) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(context)
    }

    /**
     * Provides PPGMeasure remote data source.
     *
     * @param tfmService the TFM service
     * @param userDataSource the user data source
     * @return [PPGMeasureRemoteDataSource]
     */
    @Singleton
    @Provides
    fun providePPGMeasureRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : PPGMeasureRemoteDataSource {
        return PPGMeasureRemoteDataSourceImpl(tfmService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : AccelerometerMeasureRemoteDataSource {
        return AccelerometerMeasureRemoteDataSourceImpl(tfmService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideGPSMeasureRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : GPSMeasureRemoteDataSource {
        return GPSMeasureRemoteDataSourceImpl(tfmService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideStepMeasureRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : StepMeasureRemoteDataSource {
        return StepMeasureRemoteDataSourceImpl(tfmService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : SignificantMovMeasureRemoteDataSource {
        return SignificantMovMeasureRemoteDataSourceImpl(tfmService, userDataSource)
    }

    /**
     * Provides tag remote data source.
     *
     * @param tfmService the TFM service
     * @param userDataSource the user data source
     * @return [TagRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideTagRemoteDataSource(tfmService: TFMService, userDataSource: UserCacheDataSource) : TagRemoteDataSource {
        return TagRemoteDataSourceImpl(tfmService, userDataSource)
    }
}