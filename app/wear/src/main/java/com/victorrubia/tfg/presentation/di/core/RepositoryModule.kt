package com.victorrubia.tfg.presentation.di.core

import com.victorrubia.tfg.data.repository.accelerometer_measure.AccelerometerMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.activity.ActivityRepositoryImpl
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfg.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfg.data.repository.gps_measure.GPSMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.gps_measure.datasource.GPSMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.gps_measure.datasource.GPSMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.PPGMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.SignificantMovMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.step_measure.StepMeasureRepositoryImpl
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureCacheDataSource
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureLocalDataSource
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfg.data.repository.tag.TagRepositoryImpl
import com.victorrubia.tfg.data.repository.tag.datasource.TagCacheDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfg.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfg.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides repository dependencies.
 */
@Module
class RepositoryModule {

    /**
     * Provides the [ActivityRepository] implementation.
     *
     * @param activityCacheDataSource the [ActivityCacheDataSource] implementation.
     * @param activityLocalDataSource the [ActivityLocalDataSource] implementation.
     * @param activityRemoteDataSource the [ActivityRemoteDataSource] implementation.
     * @return the [ActivityRepository] implementation.
     */
    @Singleton
    @Provides
    fun provideActivityRepository(
        activityRemoteDataSource: ActivityRemoteDataSource,
        activityLocalDataSource: ActivityLocalDataSource,
        activityCacheDataSource: ActivityCacheDataSource,
    ): ActivityRepository{
        return ActivityRepositoryImpl(
            activityRemoteDataSource,
            activityLocalDataSource,
            activityCacheDataSource
        )
    }

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
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
        userCacheDataSource: UserCacheDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource,
            userLocalDataSource,
            userCacheDataSource,
        )
    }

    /**
     * Provides the [PPGMeasureRepository] implementation.
     *
     * @param ppgMeasureCacheDataSource the [PPGMeasureCacheDataSource] implementation.
     * @param ppgMeasureLocalDataSource the [PPGMeasureLocalDataSource] implementation.
     * @param ppgMeasureRemoteDataSource the [PPGMeasureRemoteDataSource] implementation.
     * @return the [PPGMeasureRepository] implementation.
     */
    @Singleton
    @Provides
    fun providePPGMeasureRepository(
        ppgMeasureRemoteDataSource: PPGMeasureRemoteDataSource,
        ppgMeasureLocalDataSource: PPGMeasureLocalDataSource,
        ppgMeasureCacheDataSource: PPGMeasureCacheDataSource,
    ): PPGMeasureRepository {
        return PPGMeasureRepositoryImpl(
            ppgMeasureRemoteDataSource,
            ppgMeasureLocalDataSource,
            ppgMeasureCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureRepository(
        accelerometerMeasureRemoteDataSource: AccelerometerMeasureRemoteDataSource,
        accelerometerMeasureLocalDataSource: AccelerometerMeasureLocalDataSource,
        accelerometerMeasureCacheDataSource: AccelerometerMeasureCacheDataSource,
    ): AccelerometerMeasureRepository {
        return AccelerometerMeasureRepositoryImpl(
            accelerometerMeasureRemoteDataSource,
            accelerometerMeasureLocalDataSource,
            accelerometerMeasureCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideGPSMeasureRepository(
        gpsMeasureRemoteDataSource: GPSMeasureRemoteDataSource,
        gpsMeasureLocalDataSource: GPSMeasureLocalDataSource,
        gpsMeasureCacheDataSource: GPSMeasureCacheDataSource,
    ): GPSMeasureRepository {
        return GPSMeasureRepositoryImpl(
            gpsMeasureRemoteDataSource,
            gpsMeasureLocalDataSource,
            gpsMeasureCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideStepMeasureRepository(
        stepMeasureRemoteDataSource: StepMeasureRemoteDataSource,
        stepMeasureLocalDataSource: StepMeasureLocalDataSource,
        stepMeasureCacheDataSource: StepMeasureCacheDataSource,
    ): StepMeasureRepository {
        return StepMeasureRepositoryImpl(
            stepMeasureRemoteDataSource,
            stepMeasureLocalDataSource,
            stepMeasureCacheDataSource,
        )
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureRepository(
        significantMovMeasureRemoteDataSource: SignificantMovMeasureRemoteDataSource,
        significantMovMeasureLocalDataSource: SignificantMovMeasureLocalDataSource,
        significantMovMeasureCacheDataSource: SignificantMovMeasureCacheDataSource,
    ): SignificantMovMeasureRepository {
        return SignificantMovMeasureRepositoryImpl(
            significantMovMeasureRemoteDataSource,
            significantMovMeasureLocalDataSource,
            significantMovMeasureCacheDataSource,
        )
    }

    /**
     * Provides the [TagRepository] implementation.
     *
     * @param tagCacheDataSource the [TagCacheDataSource] implementation.
     * @param tagLocalDataSource the [TagLocalDataSource] implementation.
     * @param tagRemoteDataSource the [TagRemoteDataSource] implementation.
     * @return the [TagRepository] implementation.
     */
    @Singleton
    @Provides
    fun provideTagRepository(
         tagRemoteDataSource : TagRemoteDataSource,
         tagLocalDataSource: TagLocalDataSource,
         tagCacheDataSource: TagCacheDataSource
    ): TagRepository {
        return TagRepositoryImpl(
            tagRemoteDataSource,
            tagLocalDataSource,
            tagCacheDataSource,
        )
    }
}