package com.victorrubia.tfg.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.victorrubia.tfg.data.model.ppg_measure.PPGMeasure
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class PPGMeasureDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: PPGMeasureDao
    private lateinit var database: TFGDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TFGDatabase::class.java
        ).build()
        dao = database.ppgMeasureDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun savePPGMeasureTest() = runBlocking {
        val ppgMeasure =  listOf(PPGMeasure(0, Random.nextInt(0,30), Date()))
        dao.savePPGMeasure(ppgMeasure[0])
        val currentPPGMeasure = dao.getPPGMeasures()
        assertThat(currentPPGMeasure).isEqualTo(ppgMeasure)
    }

    @Test
    fun deletePPGMeasureTest() = runBlocking {
        val ppgMeasure =  PPGMeasure(0, Random.nextInt(0,30), Date())
        dao.savePPGMeasure(ppgMeasure)
        dao.deletePPGMeasures()
        val currentPPGMeasure = dao.getPPGMeasures()
        assertThat(currentPPGMeasure).isEmpty()
    }
}