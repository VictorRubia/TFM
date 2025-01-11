package com.victorrubia.tfm.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfm.data.db.*
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
     * @return the database [TFMDatabase].
     */
    @Singleton
    @Provides
    fun provideActivityDataBase(context : Context) : TFMDatabase{
        return Room.databaseBuilder(context, TFMDatabase::class.java, "tfmwear")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the activity dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfmDatabase the database.
     * @return the activity dao [ActivityDao].
     */
    @Singleton
    @Provides
    fun provideActivityDao(tfmDatabase: TFMDatabase) : ActivityDao{
        return tfmDatabase.activityDao()
    }

    @Singleton
    @Provides
    fun provideActivityAssignationDao(tfmDatabase: TFMDatabase) : ActivityAssignationDao{
        return tfmDatabase.activityAssignationDao()
    }

    /**
     * Provides the user dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfmDatabase the database.
     * @return the user dao [UserDao].
     */
    @Singleton
    @Provides
    fun provideUserDao(tfmDatabase: TFMDatabase) : UserDao{
        return tfmDatabase.userDao()
    }

    /**
     * Provides the PPGMeasure dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfmDatabase the database.
     * @return the PPGMeasure dao [PPGMeasureDao].
     */
    @Singleton
    @Provides
    fun providePPGMeasureDao(tfmDatabase: TFMDatabase) : PPGMeasureDao {
        return tfmDatabase.ppgMeasureDao()
    }

    @Singleton
    @Provides
    fun provideAccelerometerMeasureDao(tfmDatabase: TFMDatabase) : AccelerometerMeasureDao {
        return tfmDatabase.accelerometerMeasureDao()
    }

    @Singleton
    @Provides
    fun provideGPSMeasureDao(tfmDatabase: TFMDatabase) : GPSMeasureDao {
        return tfmDatabase.gpsMeasureDao()
    }

    @Singleton
    @Provides
    fun provideStepMeasureDao(tfmDatabase: TFMDatabase) : StepMeasureDao {
        return tfmDatabase.stepMeasureDao()
    }

    @Singleton
    @Provides
    fun provideSignificantMovMeasureDao(tfmDatabase: TFMDatabase) : SignificantMovMeasureDao {
        return tfmDatabase.significantMovMeasureDao()
    }

    /**
     * Provides the Tag dao.
     * Singleton annotation is necessary to make the dao available to the whole application.
     *
     * @param tfmDatabase the database.
     * @return the Tag dao [TagDao].
     */
    @Singleton
    @Provides
    fun provideTagDao(tfmDatabase: TFMDatabase) : TagDao {
        return tfmDatabase.tagDao()
    }

}