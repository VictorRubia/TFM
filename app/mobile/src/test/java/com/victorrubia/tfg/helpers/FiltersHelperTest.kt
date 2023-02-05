package com.victorrubia.tfg.helpers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FiltersHelperTest{

    private val filtersHelper: FiltersHelper = FiltersHelper()

    @Test
    fun validateEmail_returnsCorrectVeredict(){
        val validEmailAddresses = listOf("email@example.com","firstname.lastname@example.com","email@subdomain.example.com","firstname+lastname@example.com","email@123.123.123.123","1234567890@example.com","email@example-one.com","_______@example.com","email@example.name","email@example.museum","email@example.co.jp","firstname-lastname@example.com")
        val invalidEmailAddresses = listOf("plainaddress","#@%^%#$@#$@#.com","@example.com","Joe Smith <email@example.com>","email.example.com","email@example@example.com","    .email@example.com","あいうえお@example.com","email@example.com (Joe Smith)","email@example","email@-example.com","email@example..com")

        assertThat(validEmailAddresses.map{filtersHelper.validateEmail(it)}).isEqualTo(List(validEmailAddresses.size, { true }))
        assertThat(invalidEmailAddresses.map{filtersHelper.validateEmail(it)}).isEqualTo(List(invalidEmailAddresses.size, { false }))
    }

    @Test
    fun validatePassword_returnsCorrectVeredict(){
        val validPassword = "4=6ct8*9YjSJPE8}E,t"
        val invalidPassword = ""
        assertThat(filtersHelper.validatePassword(validPassword)).isTrue()
        assertThat(filtersHelper.validatePassword(invalidPassword)).isFalse()
    }

}