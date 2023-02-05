package com.victorrubia.tfg.data.repository.step_measure.datasourceImpl

import com.victorrubia.tfg.data.db.StepMeasureDao
import com.victorrubia.tfg.data.model.step_measure.StepMeasure
import com.victorrubia.tfg.data.repository.step_measure.datasource.StepMeasureLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [StepMeasureLocalDataSource] interface for retrieving data from the local database.
 * (i.e. Room database)
 *
 * @property stepMeasureDao The DAO for the [StepMeasure] table.
 */
class StepMeasureLocalDataSourceImpl(
    private val stepMeasureDao: StepMeasureDao
) : StepMeasureLocalDataSource {


    override suspend fun getStepMeasureFromDB(): List<StepMeasure> {
        return stepMeasureDao.getStepMeasures()
    }


    override suspend fun addStepMeasureToDB(stepMeasures: StepMeasure) {
        CoroutineScope(Dispatchers.IO).launch{
            stepMeasureDao.saveStepMeasure(stepMeasures)
        }
    }


    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            stepMeasureDao.deleteStepMeasures()
        }
    }
}