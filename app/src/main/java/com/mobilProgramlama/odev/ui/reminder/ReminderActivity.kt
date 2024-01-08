package com.mobilProgramlama.odev.ui.reminder

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mobilProgramlama.odev.common.Utils
import com.mobilProgramlama.odev.databinding.ActivityReminderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityReminderBinding
    private val reminderViewModel: ReminderViewModel by viewModels()
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllReminder()
        init()
    }


    private fun getAllReminder() {
        reminderViewModel.getAllReminder(System.currentTimeMillis())
        reminderViewModel.allReminder.observe(this) {
            it?.map {
                it.let {
                    mp = MediaPlayer.create(this, Uri.parse(it.sound))
                    binding.descriptionTextView.text = it.description
                    binding.titleTextView.text = it.title
                    binding.timeTextView.text = Utils.getDateStringByTimestampTime(it.time!!)
                    binding.dateTextView.text = Utils.getDateStringByTimestampDate(it.date!!)
                    mp.start()
                }
            }
        }
    }

    private fun init(){
        binding.customToolbar.customToolbarOptionalButton.visibility = View.GONE
        binding.customToolbar.customToolbarTitle.text = "Hatırlatma"
        binding.reminderAddButton2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.reminderAddButton2.id -> {
                mp.stop()
            }
        }
    }
}