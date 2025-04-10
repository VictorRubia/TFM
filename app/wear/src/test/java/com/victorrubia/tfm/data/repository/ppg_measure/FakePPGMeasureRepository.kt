package com.victorrubia.tfm.data.repository.ppg_measure

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.victorrubia.tfm.data.model.ppg_measure.PPGMeasure
import com.victorrubia.tfm.domain.repository.PPGMeasureRepository

class FakePPGMeasureRepository : PPGMeasureRepository {

    private var ppgMeasures = mutableListOf<PPGMeasure>()

    override suspend fun savePPGMeasure(ppgMeasure: PPGMeasure, activityId: Int): MutableState<Boolean> {
        ppgMeasures.add(ppgMeasure)
        return mutableStateOf(true)
    }

    override suspend fun endPPGMeasure(activityId: Int) {
        TODO("Not yet implemented")
    }
}