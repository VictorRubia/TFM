package com.victorrubia.tfg.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.model.user.UserDetails
import com.victorrubia.tfg.data.repository.user.FakeUserRepository
import com.victorrubia.tfg.domain.usecase.GetUserUseCase
import com.victorrubia.tfg.getOrAwaitValue

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : HomeViewModel

    @Before
    fun setUp() {
        val fakeUserRepository = FakeUserRepository()
        val getUserUsecase = GetUserUseCase(fakeUserRepository)
        viewModel = HomeViewModel(getUserUsecase)
    }

    @Test
    fun getUser_returnCurrentRecord(){
        val user = User("apiKey1", UserDetails("name1", "email1"))

        val currentUser = viewModel.getUser("email1", "password1").getOrAwaitValue()
        assertThat(currentUser).isEqualTo(user)
    }

}