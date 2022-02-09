package ru.barinov.bettapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import ru.barinov.bettapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findNavController(R.id.navHostFragment)


    }

    override fun onStart() {
        findNavController(R.id.navHostFragment).findDestination("homeFragment")
        super.onStart()
    }

    companion object{
        const val sharedPreferencesName = "appSharedPreferencesName"
    }




}