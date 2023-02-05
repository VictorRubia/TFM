package com.victorrubia.tfg.presentation.logged

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.R
import com.victorrubia.tfg.databinding.ActivityLoggedBinding
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.home.HomeActivity
import javax.inject.Inject

/**
 * Activity that shows the logged user's information and allows him to log out.
 * Furthermore, it allows him to see whether the wear device is connected or not.
 */
class LoggedActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: LoggedViewModelFactory
    private lateinit var loggedViewModel: LoggedViewModel
    private lateinit var binding: ActivityLoggedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logged)
        (application as Injector).createLoggedSubComponent().inject(this)

        loggedViewModel = ViewModelProvider(this, factory).get(LoggedViewModel::class.java)

        displayContent()

        loggedViewModel.sendApiKeyToWear()

        wearConnectionStatus()

    }

    /**
     * Displays whether the wear device is connected or not.
     */
    private fun wearConnectionStatus(){

        val responseLiveData = loggedViewModel.isWearConnected()
        val spannableString = SpannableString(binding.loggedWearStatus.text)

        responseLiveData.observeForever( Observer {
            if(it == true){
                spannableString.setSpan(ImageSpan(this, R.drawable.ic_check),
                    binding.loggedWearStatus.text.length-1, binding.loggedWearStatus.text.length, 0)
                binding.loggedWearStatus.text = spannableString
            }
            else if(it == false){
                spannableString.setSpan(ImageSpan(this, R.drawable.ic_error),
                    binding.loggedWearStatus.text.length-1, binding.loggedWearStatus.text.length, 0)
                binding.loggedWearStatus.text = spannableString
            }
        })

    }

    /**
     * Displays the logged user's information.
     */
    private fun displayContent(){
        val responsibleLiveData = loggedViewModel.getUser()
        responsibleLiveData.observe(this, Observer {
            if(it != null){
                binding.loggedUserName.text = it.userDetails.name
            }
            else{
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG)
            }
            Log.i("MYTAG", it.toString())
        })
    }

    /**
     * Logs out the user and starts the login activity.
     */
    fun logOut(item : MenuItem){
        when (item.itemId){
            R.id.logout -> {
                loggedViewModel.removeLocalUser()
                startActivity(Intent(this, HomeActivity::class.java));
                finish()
            }
        }
    }

}