package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfg.data.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module that provides all the necessary dependencies about the database.
 */
@Module
class DataBaseModule {

    /**
     * Provides the database.
     * Singleton annotation is necessary to make the database available to the whole application.
     *
     * @param context the context of the application.
     * @return the database [TFGDatabase].
     */
    @Singleton
    @Provides
    fun provideActivityDataBase(context : Context) : TFGDatabase{
        return Room.databaseBuilder(context, TFGDatabase::class.java, "tfgwear")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the activity dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfgDatabase the database.
     * @return the activity dao [ActivityDao].
     */
    @Singleton
    @Provides
    fun provideActivityDao(tfgDatabase: TFGDatabase) : ActivityDao{
        return tfgDatabase.activityDao()
    }

    @Singleton
    @Provides
    fun provideActivityAssignationDao(tfgDatabase: TFGDatabase) : ActivityAssignationDao{
        return tfgDatabase.activityAssignationDao()
    }

    /**
     * Provides the user dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfgDatabase the database.
     * @return the user dao [UserDao].
     */
    @Singleton
    @Provides
    fun provideUserDao(tfgDatabase: TFGDatabase) : UserDao{
        return tfgDatabase.userDao()
    }

    /**
     * Provides the PPGMeasure dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfgDatabase the database.
     * @return the PPGMeasure dao [PPGMeasureDao].
     */
    @Singleton
    @Provides
    fun providePPGMeasureDao(tfgDatabase: TFGDatabase) : PPGMeasureDao {
        return tfgDatabase.ppgMeasureDao()
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureDao(tfgDatabase: TFGDatabase) : AccelerometerMeasureDao {
        return tfgDatabase.accelerometerMeasureDao()
    }

    @Singleton
    @Provides
    fun provideGPSMeasureDao(tfgDatabase: TFGDatabase) : GPSMeasureDao {
        return tfgDatabase.gpsMeasureDao()
    }

    @Singleton
    @Provides
    fun provideStepMeasureDao(tfgDatabase: TFGDatabase) : StepMeasureDao {
        return tfgDatabase.stepMeasureDao()
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureDao(tfgDatabase: TFGDatabase) : SignificantMovMeasureDao {
        return tfgDatabase.significantMovMeasureDao()
    }

    /**
     * Provides the Tag dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfgDatabase the database.
     * @return the Tag dao [TagDao].
     */
    @Singleton
    @Provides
    fun provideTagDao(tfgDatabase: TFGDatabase) : TagDao {
        return tfgDatabase.tagDao()
    }

}