package com.victorrubia.tfg.presentation.activity_confirmation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.victorrubia.tfg.data.model.activity.Activity
import com.victorrubia.tfg.data.repository.activity.FakeActivityRepository
import com.victorrubia.tfg.domain.usecase.NewActivityUseCase
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
        val activity = Activity(0, 0, "name1", "startD1", null)

        val currentActivity = viewModel.newActivity("name1", "startD1").getOrAwaitValue()
        Truth.assertThat(currentActivity).isEqualTo(activity)
    }

}