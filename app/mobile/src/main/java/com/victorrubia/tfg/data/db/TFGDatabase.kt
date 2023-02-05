package com.victorrubia.tfg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import com.victorrubia.tfg.data.model.user.User

/**
 * Room database for TFG application.
 */
@Database(entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TFGDatabase : RoomDatabase() {

    /**
     * DAO for [User] table.
     *
     * @return [UserDao]
     */
    abstract fun userDao() : UserDao

}