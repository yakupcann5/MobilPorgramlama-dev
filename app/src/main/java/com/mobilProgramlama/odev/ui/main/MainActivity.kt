package com.mobilProgramlama.odev.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilProgramlama.odev.databinding.ActivityMainBinding
import com.mobilProgramlama.odev.ui.reminder_add.ReminderAddActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var reminderRecyclerViewAdapter: ReminderRecyclerViewAdapter
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(this, "asdasdas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        checkOverlayPermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initRecyclerView()
        getAllReminder()
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!Settings.canDrawOverlays(this)) {
                showPermissionDialog()
            }
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("İzin Gerekli")
            .setMessage("Uygulamanın düzgün çalışması için 'Diğer uygulamaların üzerinde görüntüleme' iznine ihtiyacı var. Lütfen ayarlara gidin ve izni verin.")
            .setPositiveButton("Ayarlara Git") { _, _ ->
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                requestPermissionLauncher.launch(intent)
            }
            .setNegativeButton("İptal") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getAllReminder() {
        mainActivityViewModel.getAllReminder()
        mainActivityViewModel.allReminder.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.reminderRecyclerView.visibility = View.GONE
                binding.reminderEmptyView.visibility = View.VISIBLE
            } else {
                binding.reminderRecyclerView.visibility = View.VISIBLE
                binding.reminderEmptyView.visibility = View.GONE
                reminderRecyclerViewAdapter.submitList(it as ArrayList)
            }
        }
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