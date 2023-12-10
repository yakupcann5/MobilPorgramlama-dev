package com.mobilProgramlama.odev.data.locale.repository

import com.mobilProgramlama.odev.data.locale.dao.reminder.ReminderDao
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.repository.reminder.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(private val reminderDao: ReminderDao) : ReminderRepository {
    override suspend fun insertReminder(reminder: ReminderEntity) {
        reminderDao.insertReminder(reminder)
    }
}