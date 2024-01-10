package com.mobilProgramlama.odev.ui.completed_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobilProgramlama.odev.R
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.databinding.CompletedListItemBinding

class CompletedListRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var reminder: ArrayList<ReminderEntity> = arrayListOf()

    class ViewHolder(view: CompletedListItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        val sentBinding: CompletedListItemBinding = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CompletedListItemBinding>(
            inflater,
            R.layout.completed_list_item, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reminder.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).sentBinding.reminder = reminder[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<ReminderEntity>) {
        reminder.clear()
        reminder.addAll(list)
        notifyDataSetChanged()
    }
}