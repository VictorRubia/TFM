package com.victorrubia.tfm.presentation.di.core

import com.victorrubia.tfm.data.db.*
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasourceImpl.AccelerometerMeasureLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.activity.datasource.ActivityLocalDataSource
import com.victorrubia.tfm.data.repository.activity.datasourceImpl.ActivityLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.activity_assignation.datasource.ActivityAssignationLocalDataSource
import com.victorrubia.tfm.data.repository.activity_assignation.datasourceImpl.ActivityAssignationLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.gps_measure.datasourceImpl.GPSMeasureLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.ppg_measure.datasource.PPGMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.ppg_measure.datasourceImpl.PPGMeasureLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.significantMov_measure.datasource.SignificantMovMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.significantMov_measure.datasourceImpl.SignificantMovMeasureLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.step_measure.datasource.StepMeasureLocalDataSource
import com.victorrubia.tfm.data.repository.step_measure.datasourceImpl.StepMeasureLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.tag.datasource.TagLocalDataSource
import com.victorrubia.tfm.data.repository.tag.datasourceImpl.TagLocalDataSourceImpl
import com.victorrubia.tfm.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfm.data.repository.user.datasourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides local data sources dependencies.
 */
@Module
class LocalDataModule {

    /**
     * Provides the local data source for the activity repository.
     *
     * @param activityDao The activity DAO.
     * @return The local data source [ActivityLocalDataSource].
     */
    @Singleton
    @Provides
    fun provideActivityLocalDataSource(activityDao: ActivityDao) : ActivityLocalDataSource{
        return ActivityLocalDataSourceImpl(activityDao)
    }

    @Singleton
    @Provides
    fun provideActivityAssignationLocalDataSource(activityAssignationDao: ActivityAssignationDao) : ActivityAssignationLocalDataSource {
        return ActivityAssignationLocalDataSourceImpl(activityAssignationDao)
    }


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

    /**
     * Provides the local data source for the PPGMeasure repository.
     *
     * @param ppgMeasureDao The PPGMeasure DAO.
     * @return The local data source [PPGMeasureLocalDataSource].
     */
    @Singleton
    @Provides
    fun providePPGMeasureLocalDataSource(ppgMeasureDao: PPGMeasureDao) : PPGMeasureLocalDataSource {
        return PPGMeasureLocalDataSourceImpl(ppgMeasureDao)
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureLocalDataSource(accelerometerMeasureDao: AccelerometerMeasureDao) : AccelerometerMeasureLocalDataSource {
        return AccelerometerMeasureLocalDataSourceImpl(accelerometerMeasureDao)
    }

    @Singleton
    @Provides
    fun provideGPSMeasureLocalDataSource(gpsMeasureDao: GPSMeasureDao) : GPSMeasureLocalDataSource {
        return GPSMeasureLocalDataSourceImpl(gpsMeasureDao)
    }

    @Singleton
    @Provides
    fun provideStepMeasureLocalDataSource(stepMeasureDao: StepMeasureDao) : StepMeasureLocalDataSource {
        return StepMeasureLocalDataSourceImpl(stepMeasureDao)
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureLocalDataSource(significantMovMeasureDao: SignificantMovMeasureDao) : SignificantMovMeasureLocalDataSource {
        return SignificantMovMeasureLocalDataSourceImpl(significantMovMeasureDao)
    }

    /**
     * Provides the local data source for the tag repository.
     *
     * @param tagDao The tag DAO.
     * @return The local data source [TagLocalDataSource].
     */
    @Singleton
    @Provides
    fun provideTagLocalDataSource(tagDao: TagDao) : TagLocalDataSource {
        return TagLocalDataSourceImpl(tagDao)
    }


}