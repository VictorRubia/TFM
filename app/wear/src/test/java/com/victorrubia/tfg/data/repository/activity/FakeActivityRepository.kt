package com.victorrubia.tfg.data.repository.activity

import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.domain.repository.ActivityRepository

class FakeActivityRepository(private var activity: Activity? = null) : ActivityRepository {

    override suspend fun newActivity(name: String, startTimestamp: String): Activity {
        activity = Activity(0, 0, "name1", "startD1", null)
        return activity!!
    }

    override suspend fun getCurrentActivity(): Activity? {
        return this.activity
    }

    override suspend fun endActivity(): Activity? {
        activity = activity?.let { Activity(activity!!.id, activity!!.userId, activity!!.name, it.startD, "endD1") }
        return activity
    }
}