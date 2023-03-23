package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import com.victorrubia.tfg.data.api.TFGService
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasourceImpl.AccelerometerMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.datasourceImpl.ActivityRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfg.data.repository.activity_assignation.datasourceImpl.ActivityAssignationRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.gps_measure.datasourceImpl.GPSMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasourceImpl.PPGMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.datasourceImpl.SignificantMovMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.step_measure.datasourceImpl.StepMeasureRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.data.repository.tag.datasourceImpl.TagRemoteDataSourceImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.data.repository.user.datasourceImpl.UserRemoteDataSourceImpl
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
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [ActivityRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideActivityRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : ActivityRemoteDataSource{
        return ActivityRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideActivityAssignationRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : ActivityAssignationRemoteDataSource {
        return ActivityAssignationRemoteDataSourceImpl(tfgService, userDataSource)
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
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [PPGMeasureRemoteDataSource]
     */
    @Singleton
    @Provides
    fun providePPGMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : PPGMeasureRemoteDataSource {
        return PPGMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : AccelerometerMeasureRemoteDataSource {
        return AccelerometerMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideGPSMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : GPSMeasureRemoteDataSource {
        return GPSMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideStepMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : StepMeasureRemoteDataSource {
        return StepMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : SignificantMovMeasureRemoteDataSource {
        return SignificantMovMeasureRemoteDataSourceImpl(tfgService, userDataSource)
    }

    /**
     * Provides tag remote data source.
     *
     * @param tfgService the TFG service
     * @param userDataSource the user data source
     * @return [TagRemoteDataSource]
     */
    @Singleton
    @Provides
    fun provideTagRemoteDataSource(tfgService: TFGService, userDataSource: UserCacheDataSource) : TagRemoteDataSource {
        return TagRemoteDataSourceImpl(tfgService, userDataSource)
    }
}