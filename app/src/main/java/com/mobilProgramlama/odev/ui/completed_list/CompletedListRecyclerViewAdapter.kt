package com.mobilProgramlama.odev.ui.completed_list

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.databinding.ReminderItemBinding
import com.mobilProgramlama.odev.ui.main.ReminderRecyclerViewAdapter

class CompletedListRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var reminder: ArrayList<ReminderEntity> = arrayListOf()

    class ViewHolder(view: ReminderItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        val sentBinding: ReminderItemBinding = view
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return reminder.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReminderRecyclerViewAdapter.ViewHolder).sentBinding.reminder = reminder[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<ReminderEntity>) {
        reminder.clear()
        reminder.addAll(list)
        notifyDataSetChanged()
    }
}