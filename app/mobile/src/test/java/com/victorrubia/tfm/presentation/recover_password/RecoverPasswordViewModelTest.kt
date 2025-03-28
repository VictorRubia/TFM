package com.victorrubia.tfm.presentation.recover_password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.victorrubia.tfm.data.model.user.User
import com.victorrubia.tfm.data.model.user.UserDetails
import com.victorrubia.tfm.data.repository.user.FakeUserRepository
import com.victorrubia.tfm.domain.usecase.RecoverPasswordUseCase
import com.victorrubia.tfm.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecoverPasswordViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RecoverPasswordViewModel

    @Before
    fun setUp(){
        val fakeUserRepository = FakeUserRepository()
        val recoverPasswordUseCase = RecoverPasswordUseCase(fakeUserRepository)
        viewModel = RecoverPasswordViewModel(recoverPasswordUseCase)
    }

    @Test
    fun recoverPassword_returnTrue(){
        val user = User("apiKey1", UserDetails("name1", "validemail@email.com"))

        val isCorrect = viewModel.requestPasswordReminder(user.userDetails.email).getOrAwaitValue()
        assertThat(isCorrect).isTrue()
    }
}