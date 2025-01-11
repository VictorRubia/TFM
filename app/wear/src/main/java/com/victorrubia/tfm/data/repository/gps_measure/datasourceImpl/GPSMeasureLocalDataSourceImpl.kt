package com.victorrubia.tfm.data.repository.gps_measure.datasourceImpl

import com.victorrubia.tfm.data.db.GPSMeasureDao
import com.victorrubia.tfm.data.model.gps_measure.GPSMeasure
import com.victorrubia.tfm.data.repository.gps_measure.datasource.GPSMeasureLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [GPSMeasureLocalDataSource] interface for retrieving data from the local database.
 * (i.e. Room database)
 *
 * @property gpsMeasureDao The DAO for the [GPSMeasure] table.
 */
class GPSMeasureLocalDataSourceImpl(
    private val gpsMeasureDao: GPSMeasureDao
) : GPSMeasureLocalDataSource {


    override suspend fun getGPSMeasureFromDB(): List<GPSMeasure> {
        return gpsMeasureDao.getGPSMeasures()
    }


    override suspend fun addGPSMeasureToDB(gpsMeasures: GPSMeasure) {
        CoroutineScope(Dispatchers.IO).launch{
            gpsMeasureDao.saveGPSMeasure(gpsMeasures)
        }
    }


    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            gpsMeasureDao.deleteGPSMeasures()
        }
    }
}