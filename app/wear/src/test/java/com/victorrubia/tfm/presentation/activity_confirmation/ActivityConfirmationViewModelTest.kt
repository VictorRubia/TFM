package com.victorrubia.tfm.presentation.activity_confirmation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.victorrubia.tfm.data.model.activity.Activity
import com.victorrubia.tfm.data.repository.activity.FakeActivityRepository
import com.victorrubia.tfm.domain.usecase.NewActivityUseCase
import getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityConfirmationViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ActivityConfirmationViewModel

    @Before
    fun setUp() {
        val fakeActivityRepository = FakeActivityRepository()
        val newActivityUseCase = NewActivityUseCase(fakeActivityRepository)
        viewModel = ActivityConfirmationViewModel(newActivityUseCase)
    }

    @Test
    fun newActivity_returnCurrentRecord(){
        val activity = Activity(0, 0, 0, "startD1", null)

        val currentActivity = viewModel.newActivity(0, "startD1").getOrAwaitValue()
        Truth.assertThat(currentActivity).isEqualTo(activity)
    }

}