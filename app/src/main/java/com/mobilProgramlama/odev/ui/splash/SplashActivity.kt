package com.mobilProgramlama.odev.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.databinding.ActivitySplashBinding
import com.mobilProgramlama.odev.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startMainActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}