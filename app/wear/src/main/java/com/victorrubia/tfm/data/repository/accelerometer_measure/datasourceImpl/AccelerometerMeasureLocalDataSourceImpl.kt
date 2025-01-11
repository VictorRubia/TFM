package com.victorrubia.tfm.data.repository.accelerometer_measure.datasourceImpl

import com.victorrubia.tfm.data.db.AccelerometerMeasureDao
import com.victorrubia.tfm.data.model.accelerometer_measure.AccelerometerMeasure
import com.victorrubia.tfm.data.repository.accelerometer_measure.datasource.AccelerometerMeasureLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [AccelerometerMeasureLocalDataSource] interface for retrieving data from the local database.
 * (i.e. Room database)
 *
 * @property accelerometerMeasureDao The DAO for the [AccelerometerMeasure] table.
 */
class AccelerometerMeasureLocalDataSourceImpl(
    private val accelerometerMeasureDao: AccelerometerMeasureDao
) : AccelerometerMeasureLocalDataSource {


    override suspend fun getAccelerometerMeasureFromDB(): List<AccelerometerMeasure> {
        return accelerometerMeasureDao.getAccelerometerMeasures()
    }


    override suspend fun addAccelerometerMeasureToDB(accelerometerMeasures: AccelerometerMeasure) {
        CoroutineScope(Dispatchers.IO).launch{
            accelerometerMeasureDao.saveAccelerometerMeasure(accelerometerMeasures)
        }
    }


    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            accelerometerMeasureDao.deleteAccelerometerMeasures()
        }
    }
}