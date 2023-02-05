package com.victorrubia.tfg.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.R
import com.victorrubia.tfg.databinding.ActivityHomeBinding
import com.victorrubia.tfg.helpers.FiltersHelper
import com.victorrubia.tfg.helpers.LottieHelper
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.logged.LoggedActivity
import com.victorrubia.tfg.presentation.recover_password.RecoverPasswordActivity
import javax.inject.Inject

/**
 * Activity that shows the home screen where the user can choose to log in or recover password
 */
class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    private lateinit var animacion : LottieHelper
    private var filters = FiltersHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        (application as Injector).createHomeSubComponent()
            .inject(this)

        homeViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        animacion = LottieHelper(binding.homeAnimationLogin)

        binding.homeLogin.setOnClickListener {
            if(filters.validateEmailTextInput(binding.homeEmailText) &&
                filters.validatePasswordTextInput(binding.homePasswordText)) loginUser()
        }

        binding.homeRecoverPassword.setOnClickListener{
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }
    }

    /**
     * Method that checks if the user is logged in and if so, starts the logged activity
     */
    private fun loginUser(){
        val loggedPage = Intent(this, LoggedActivity::class.java)
        animacion.showLoading()
        val responseLiveData = homeViewModel.getUser(binding.homeEmailText.text.toString(),
            binding.homePasswordText.text.toString())

        responseLiveData.observe(this, Observer{
            if(it != null){
                animacion.showLoadingSuccessful(this, loggedPage)
            }
            else{
                animacion.showLoadingError()
                Toast.makeText(this, "Usuario no encontrado!", Toast.LENGTH_LONG).show()
            }
            Log.i("MYTAG", it.toString())
        })
    }

}