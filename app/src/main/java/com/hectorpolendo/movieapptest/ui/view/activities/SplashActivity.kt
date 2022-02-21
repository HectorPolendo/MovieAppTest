package com.hectorpolendo.movieapptest.ui.view.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.hectorpolendo.movieapptest.databinding.ActivitySplashBinding
import com.hectorpolendo.movieapptest.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.onCreate(this@SplashActivity)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        splashViewModel.progress.observe(this,{
            binding.progressbar.setProgress(it)
            if(it==100){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }
}