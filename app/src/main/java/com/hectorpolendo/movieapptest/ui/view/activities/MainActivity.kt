package com.hectorpolendo.movieapptest.ui.view.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.hectorpolendo.movieapptest.R
import com.hectorpolendo.movieapptest.databinding.ActivityMainBinding
import com.hectorpolendo.movieapptest.util.Constants
import com.hectorpolendo.movieapptest.util.LocationTime
import com.hectorpolendo.movieapptest.util.Methods
import com.hectorpolendo.movieapptest.util.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(!Methods.hasPermissions(this)){
            ActivityCompat.requestPermissions(this@MainActivity, Constants.PERMISSIONS, 1)
        }
        NetworkConnection.initialize(this)
        LocationTime.initialize()

        NavigationUI.setupWithNavController(binding.bottomNav,
            Navigation.findNavController(this, R.id.hostFragment))

        NetworkConnection.isConnected
            .onEach { locationObserver() }
            .launchIn(lifecycleScope)

    }

    private fun locationObserver(){
        LocationTime.isCounterRunning
            .onEach {
                if(!it)
                    Methods.getLocation(applicationContext, this@MainActivity)
            }
            .launchIn(lifecycleScope)
    }
}