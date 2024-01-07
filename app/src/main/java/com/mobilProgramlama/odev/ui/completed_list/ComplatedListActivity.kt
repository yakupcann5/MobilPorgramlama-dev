package com.mobilProgramlama.odev.ui.completed_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.databinding.ActivityComplatedListBinding
import com.mobilProgramlama.odev.ui.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplatedListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityComplatedListBinding
    private val completeListActivityViewModel: CompleteListActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplatedListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getAllCompleteReminderList()
    }

    private fun getAllCompleteReminderList() {
        completeListActivityViewModel.getAllCompleteReminderList()
    }

    private fun init() {
        binding.customToolbar.customToolbarOptionalButton.visibility = View.GONE
        binding.customToolbar.customToolbarDoneButton.visibility = View.VISIBLE
        binding.customToolbar.customToolbarTitle.text = "Tamamlanmş Görevler"
        binding.customToolbar.customToolbarDoneButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.customToolbar.customToolbarDoneButton.id -> {

            }
        }
    }
}