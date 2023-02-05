package com.victorrubia.tfg.presentation.recover_password

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.R
import com.victorrubia.tfg.databinding.ActivityRecoverPasswordBinding
import com.victorrubia.tfg.helpers.FiltersHelper
import com.victorrubia.tfg.helpers.LottieHelper
import com.victorrubia.tfg.presentation.di.Injector
import javax.inject.Inject

/**
 * Activity that allows the user to remind his password
 */
class RecoverPasswordActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: RecoverPasswordViewModelFactory
    private lateinit var recoverPasswordViewModel: RecoverPasswordViewModel
    private lateinit var binding: ActivityRecoverPasswordBinding

    private lateinit var animacion : LottieHelper
    private var filters = FiltersHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recover_password)
        (application as Injector).createRecoverPasswordSubComponent().inject(this)

        recoverPasswordViewModel = ViewModelProvider(this, factory).get(RecoverPasswordViewModel::class.java)

        animacion = LottieHelper(binding.animacionCheckMail)
        setSupportActionBar(binding.recoverPasswordAppBar)
        binding.recoverPasswordAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.recoverPasswordSendEmailButton.setOnClickListener{
            if(filters.validateEmailTextInput(binding.recoverPasswordEmailText))
                sendRequest()
        }

    }

    /**
     * Process the filled in email and sends a request to the server, showing a loading animation and a message.
     */
    private fun sendRequest(){
        animacion.showLoading()
        val responseLiveData = recoverPasswordViewModel.requestPasswordReminder(binding.recoverPasswordEmailText.text.toString())

        responseLiveData.observe(this, Observer {
            if((it != null) && it){
                animacion.showLoadingSuccessful(null, null)
                Toast.makeText(applicationContext, "Correo enviado", Toast.LENGTH_LONG).show()
            }
            else{
                animacion.showLoadingError()
                Toast.makeText(applicationContext, "Usuario no existente!", Toast.LENGTH_LONG).show()
            }
            Log.i("MYTAG", it.toString())
        })

    }


}