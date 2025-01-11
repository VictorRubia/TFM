package com.victorrubia.tfm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfm.data.converters.Converters
import com.victorrubia.tfm.data.model.user.User

/**
 * Room database for TFM application.
 */
@Database(entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFMDatabase : RoomDatabase() {

    /**
     * DAO for [User] table.
     *
     * @return [UserDao]
     */
    abstract fun userDao() : UserDao

}