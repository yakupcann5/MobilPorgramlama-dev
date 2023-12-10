package com.mobilProgramlama.odev.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.databinding.ActivityMainBinding
import com.mobilProgramlama.odev.ui.reminder_add.ReminderAddActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reminderRecyclerViewAdapter: ReminderRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initRecyclerView()
    }

    private fun initViews() {
        binding.customToolbar.customToolbarTitle.text = "Görev Listesi"
        binding.customToolbar.customToolbarOptionalButton.setOnClickListener(this)
        binding.reminderAddButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        reminderRecyclerViewAdapter = ReminderRecyclerViewAdapter()
        binding.reminderRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.reminderRecyclerView.adapter = reminderRecyclerViewAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.customToolbar.customToolbarOptionalButton.id -> {

            }
            binding.reminderAddButton.id -> {
                val intent = Intent(this, ReminderAddActivity::class.java)
                startActivity(intent)
            }
        }
    }
}