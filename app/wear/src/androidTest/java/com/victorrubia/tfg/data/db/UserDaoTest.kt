package com.victorrubia.tfg.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.victorrubia.tfg.data.model.user.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: UserDao
    private lateinit var database: TFGDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TFGDatabase::class.java
        ).build()
        dao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveUserTest() = runBlocking {
        val user = User("apiKey1")
        dao.saveUser(user)
        val currentUser = dao.getUser()
        Truth.assertThat(currentUser).isEqualTo(user)
    }

    @Test
    fun deleteUserTest() = runBlocking {
        val user = User("apiKey1")
        dao.saveUser(user)
        dao.deleteUser()
        val currentUser = dao.getUser()
        Truth.assertThat(currentUser).isNull()
    }
}