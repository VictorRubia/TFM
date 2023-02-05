package com.victorrubia.tfg.data.repository.significantMov_measure.datasourceImpl

import com.victorrubia.tfg.data.db.SignificantMovMeasureDao
import com.victorrubia.tfg.data.model.significant_mov_measure.SignificantMovMeasure
import com.victorrubia.tfg.data.repository.significantMov_measure.datasource.SignificantMovMeasureLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of the [SignificantMovMeasureLocalDataSource] interface for retrieving data from the local database.
 * (i.e. Room database)
 *
 * @property significantMovMeasureDao The DAO for the [SignificantMovMeasure] table.
 */
class SignificantMovMeasureLocalDataSourceImpl(
    private val significantMovMeasureDao: SignificantMovMeasureDao
) : SignificantMovMeasureLocalDataSource {


    override suspend fun getSignificantMovMeasureFromDB(): List<SignificantMovMeasure> {
        return significantMovMeasureDao.getSignificantMovMeasures()
    }


    override suspend fun addSignificantMovMeasureToDB(significantMovMeasures: SignificantMovMeasure) {
        CoroutineScope(Dispatchers.IO).launch{
            significantMovMeasureDao.saveSignificantMovMeasure(significantMovMeasures)
        }
    }


    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            significantMovMeasureDao.deleteSignificantMovMeasures()
        }
    }
}