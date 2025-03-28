package com.victorrubia.tfm.data.repository.activity

import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.domain.repository.ActivityRepository

class FakeActivityRepository(private var activity: Activity? = null) : ActivityRepository {

    override suspend fun newActivity(activityId: Int, startTimestamp: String): Activity {
        activity = Activity(0, 0, 0, "startD1", null)
        return activity!!
    }

    override suspend fun getCurrentActivity(): Activity? {
        return this.activity
    }

    override suspend fun endActivity(): Activity? {
        activity = activity?.let { Activity(activity!!.id, activity!!.userId, activity!!.activitiesRepositoryId, it.startD, "endD1") }
        return activity
    }
}