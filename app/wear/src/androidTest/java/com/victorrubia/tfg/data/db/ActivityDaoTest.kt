package com.victorrubia.tfg.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.victorrubia.tfg.data.model.activity.Activity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: ActivityDao
    private lateinit var database: TFGDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TFGDatabase::class.java
        ).build()
        dao = database.activityDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveActivityTest() = runBlocking {
        val activity = Activity(0, 0, "name1", "startD1", null)
        dao.saveActivity(activity)
        val currentActivity = dao.getActivity()
        Truth.assertThat(currentActivity).isEqualTo(activity)
    }

    @Test
    fun deleteActivityTest() = runBlocking {
        val activity = Activity(0, 0, "name1", "startD1", null)
        dao.saveActivity(activity)
        dao.deleteActivity()
        val currentActivity = dao.getActivity()
        Truth.assertThat(currentActivity).isNull()
    }

}