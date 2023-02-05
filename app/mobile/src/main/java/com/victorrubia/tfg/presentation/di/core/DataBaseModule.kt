package com.victorrubia.tfg.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.victorrubia.tfg.data.db.TFGDatabase
import com.victorrubia.tfg.data.db.UserDao
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
    fun provideUserDataBase(context : Context) : TFGDatabase {
        return Room.databaseBuilder(context, TFGDatabase::class.java, "tfgclient")
            .build()
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
    fun provideUserDao(tfgDatabase: TFGDatabase) : UserDao {
        return tfgDatabase.userDao()
    }
}