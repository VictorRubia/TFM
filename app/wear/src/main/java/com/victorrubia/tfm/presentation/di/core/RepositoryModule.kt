package com.victorrubia.tfm.presentation.di.core

import com.victorrubia.tfm.data.repository.accelerometer_measure.AccelerometerMeasureRepositoryImpl
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.activity.ActivityRepositoryImpl
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityCacheDataSource
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityRemoteDataSource
import com.victorrubia.tfm.data.repository.activity_assignation.ActivityAssignationRepositoryImpl
import com.victorrubia.tfm.data.repository.activity_assignation.datasource.ActivityAssignationCacheDataSource
import com.victorrubia.tfm.data.repository.activity_assignation.datasource.ActivityAssignationLocalDataSource
import com.victorrubia.tfm.data.repository.activity_assignation.datasource.ActivityAssignationRemoteDataSource
import com.victorrubia.tfm.data.repository.gps_measure.GPSMeasureRepositoryImpl
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.ppg_measure.PPGMeasureRepositoryImpl
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.significantMov_measure.SignificantMovMeasureRepositoryImpl
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.step_measure.StepMeasureRepositoryImpl
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureCacheDataSource
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureRemoteDataSource
import com.victorrubia.tfm.data.repository.tag.TagRepositoryImpl
import com.victorrubia.tfm.data.repository.tag.datasource.TagCacheDataSource
import com.victorrubia.tfm.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfm.data.repository.tag.datasource.TagRemoteDataSource
import com.victorrubia.tfm.data.repository.user.UserRepositoryImpl
import com.victorrubia.tfm.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfm.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfm.domain.repository.*
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

    @Singleton
    @Provides
    fun provideActivityAssignationRepository(
        activityAssignationRemoteDataSource: ActivityAssignationRemoteDataSource,
        activityAssignationLocalDataSource: ActivityAssignationLocalDataSource,
        activityAssignationCacheDataSource: ActivityAssignationCacheDataSource,
    ): ActivityAssignationRepository{
        return ActivityAssignationRepositoryImpl(
            activityAssignationRemoteDataSource,
            activityAssignationLocalDataSource,
            activityAssignationCacheDataSource
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