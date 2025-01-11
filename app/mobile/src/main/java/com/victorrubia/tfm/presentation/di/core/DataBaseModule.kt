package com.victorrubia.tfm.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfm.data.db.TFMDatabase
import com.victorrubia.tfm.data.db.UserDao
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
    fun provideUserDataBase(context : Context) : TFMDatabase {
        return Room.databaseBuilder(context, TFMDatabase::class.java, "tfmclient")
            .build()
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
    fun provideUserDao(tfmDatabase: TFMDatabase) : UserDao {
        return tfmDatabase.userDao()
    }
}