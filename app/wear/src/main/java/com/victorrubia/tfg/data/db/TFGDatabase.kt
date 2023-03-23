package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.model.activity_repository.ActivityAssignation
import com.victorrubia.tfg.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfg.data.model.step_measure.StepMeasure
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfg.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfg.data.model.tag.Tag
import com.victorrubia.tfg.data.model.user.User

/**
 * Room database for TFG application.
 */
@Database(entities = [Activity::class, PPGMeasure::class, AccelerometerMeasure::class, GPSMeasure::class, StepMeasure::class, SignificantMovMeasure::class , User::class, Tag::class, ActivityAssignation::class],
version = 10,
exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFGDatabase : RoomDatabase(){
    /**
     * DAO for [Activity] table.
     *
     * @return [ActivityDao]
     */
    abstract fun activityDao() : ActivityDao

    /**
     * DAO for [PPGMeasure] table.
     *
     * @return [PPGMeasureDao]
     */
    abstract fun ppgMeasureDao() : PPGMeasureDao

    abstract fun accelerometerMeasureDao() : AccelerometerMeasureDao

    abstract fun gpsMeasureDao() : GPSMeasureDao

    abstract fun stepMeasureDao() : StepMeasureDao

    abstract fun significantMovMeasureDao() : SignificantMovMeasureDao

    /**
     * DAO for [User] table.
     *
     * @return [UserDao]
     */
    abstract fun userDao() : UserDao

    /**
     * DAO for [Tag] table.
     *
     * @return [TagDao]
     */
    abstract fun tagDao() : TagDao

    abstract fun activityAssignationDao() : ActivityAssignationDao
}