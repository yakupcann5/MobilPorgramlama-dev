package com.mobilProgramlama.odev.ui.completed_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.databinding.ActivityComplatedListBinding
import com.mobilProgramlama.odev.ui.main.MainActivityViewModel
import com.mobilProgramlama.odev.ui.main.ReminderRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplatedListActivity : AppCompatActivity(), View.OnClickListener, ReminderOnClickListener {
    private lateinit var binding: ActivityComplatedListBinding
    private lateinit var completedListRecyclerViewAdapter: CompletedListRecyclerViewAdapter
    private val completeListActivityViewModel: CompleteListActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplatedListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initRecyclerView()
        getAllCompleteReminderList()
    }

    private fun initRecyclerView() {
        completedListRecyclerViewAdapter = CompletedListRecyclerViewAdapter(this)
        binding.completedListRc.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.completedListRc.adapter = completedListRecyclerViewAdapter

    }

    private fun getAllCompleteReminderList() {
        completeListActivityViewModel.getAllCompleteReminderList(System.currentTimeMillis())
        completeListActivityViewModel.completeList.observe(this) {
            it.let {
                if (it.isNullOrEmpty()) {
                    binding.completedListRc.visibility = View.GONE
                    binding.reminderEmptyView.visibility = View.VISIBLE
                } else {
                    binding.completedListRc.visibility = View.VISIBLE
                    binding.reminderEmptyView.visibility = View.GONE
                    completedListRecyclerViewAdapter.submitList(it as ArrayList)
                }
            }
        }
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

    override fun onClick(reminder: ReminderEntity) {
        completeListActivityViewModel.deleteReminder(reminder)
        completeListActivityViewModel.deleteReminderSuccess.observe(this) {
            if (it) {
                getAllCompleteReminderList()
                Toast.makeText(this, "Alarm Silme İşlemi Tamamlandı", Toast.LENGTH_LONG).show()
            }
        }
    }
}