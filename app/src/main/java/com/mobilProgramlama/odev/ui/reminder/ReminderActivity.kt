package com.mobilProgramlama.odev.ui.reminder

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mobilProgramlama.odev.databinding.ActivityReminderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderBinding
    private val deneme: ReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllReminder()

    }

    private fun getAllReminder() {
        deneme.getAllReminder(System.currentTimeMillis())
        deneme.allReminder.observe(this) {
            it?.map {
                it.let {
                    val mp: MediaPlayer = MediaPlayer.create(this, Uri.parse(it.sound))
                    Log.d("muhammed123123", "getAllReminder: ${it.sound}")
                    mp.start()
                }
            }
        }
    }
}