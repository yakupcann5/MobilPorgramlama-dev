package com.mobilProgramlama.odev.ui.completed_list

import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

interface ReminderOnClickListener {
    fun onClick(reminder: ReminderEntity)
}