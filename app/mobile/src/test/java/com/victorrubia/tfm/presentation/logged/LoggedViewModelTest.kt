package com.victorrubia.tfm.presentation.logged

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.victorrubia.tfm.data.repository.user.FakeUserRepository
import com.victorrubia.tfm.domain.usecase.GetUserUseCase
import com.victorrubia.tfm.domain.usecase.IsWearConnectedUseCase
import com.victorrubia.tfm.domain.usecase.RemoveLocalUserUseCase
import com.victorrubia.tfm.domain.usecase.SendApiKeyToWearUseCase
import com.victorrubia.tfm.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoggedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : LoggedViewModel

    @Before
    fun setUp() {
        val fakeUserRepository = FakeUserRepository()
        val getUserUsecase = GetUserUseCase(fakeUserRepository)
        val removeLocalUserUseCase = RemoveLocalUserUseCase(fakeUserRepository)
        val sendApiKeyToWearUseCase = SendApiKeyToWearUseCase(fakeUserRepository)
        val isWearConnectedUseCase = IsWearConnectedUseCase(fakeUserRepository)
        viewModel = LoggedViewModel(getUserUsecase, removeLocalUserUseCase, sendApiKeyToWearUseCase, isWearConnectedUseCase)
    }

    @Test
    fun removeUser_returnsNull(){
        viewModel.getUser("email1", "password1").getOrAwaitValue()
        viewModel.removeLocalUser()
        val currentUser = viewModel.getUser().getOrAwaitValue()
        Truth.assertThat(currentUser).isNull()
    }

}