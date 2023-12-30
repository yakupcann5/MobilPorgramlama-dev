package com.mobilProgramlama.odev.ui.reminder_add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.common.Timer
import com.mobilProgramlama.odev.databinding.ActivityReminderAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderAddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReminderAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)
    }

    private fun initView() {
        binding.saveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.saveButton.id -> {
                //viewModel.checkInput()
                //viewModel.insertReminder()
                //viewModel.checkInsertReminderResult()
            }
        }
    }
}