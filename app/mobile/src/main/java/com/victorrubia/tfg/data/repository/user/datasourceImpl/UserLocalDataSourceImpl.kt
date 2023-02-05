package com.victorrubia.tfg.data.repository.user.datasourceImpl

import com.victorrubia.tfg.data.db.UserDao
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [UserLocalDataSource] that saves data on the local database.
 * @property userDao The [UserDao] that provides access to the local database.
 */
class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun getUserFromDB(): User {
        return userDao.getUser()
    }

    override suspend fun saveUserToDB(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.saveUser(user)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUser()
        }
    }
}