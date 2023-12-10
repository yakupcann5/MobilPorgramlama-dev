package com.mobilProgramlama.odev.ui.reminder_add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.databinding.ActivityReminderAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}