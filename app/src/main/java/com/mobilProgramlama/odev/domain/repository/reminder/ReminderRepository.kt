package com.mobilProgramlama.odev.domain.repository.reminder

import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

interface ReminderRepository {
    suspend fun insertReminder(reminder: ReminderEntity): Long
    suspend fun getAllReminder(currentTime: Long): List<ReminderEntity>
    suspend fun getAllCompletedReminder(currentTime: Long): List<ReminderEntity>
    suspend fun deleteReminder(id: Int)
}