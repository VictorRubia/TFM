package com.victorrubia.tfg.presentation.entry

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.victorrubia.tfg.presentation.di.Injector
import com.victorrubia.tfg.presentation.home.HomeActivity
import com.victorrubia.tfg.presentation.logged.LoggedActivity
import javax.inject.Inject

/**
 * Activity that decides which activity to start depending on the user's state
 * If it's logged, it starts the LoggedActivity, if it's not, it starts the HomeActivity
 */
class EntryActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: EntryViewModelFactory
    private lateinit var entryViewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Injector).createEntrySubComponent().inject(this)

        entryViewModel = ViewModelProvider(this, factory).get(EntryViewModel::class.java)

        previouslyLogged()
    }

    /**
     * If the user is logged, it starts the LoggedActivity,
     * otherwise it starts the HomeActivity for the user to log in
     */
    private fun previouslyLogged(){
        val loggedPage = Intent(this, LoggedActivity::class.java)
        val homePage = Intent(this, HomeActivity::class.java)

        val responseLiveData = entryViewModel.getUser()
        responseLiveData.observe(this, Observer {
            if(it != null){
                startActivity(loggedPage)
            }
            else{
                startActivity(homePage)
            }
            finish()
        })
    }
}