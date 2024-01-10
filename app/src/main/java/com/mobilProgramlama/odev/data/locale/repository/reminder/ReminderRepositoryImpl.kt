package com.mobilProgramlama.odev.data.locale.repository.reminder

import com.mobilProgramlama.odev.data.locale.dao.reminder.ReminderDao
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.repository.reminder.ReminderRepository
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(private val reminderDao: ReminderDao) :
    ReminderRepository {
    override suspend fun insertReminder(reminder: ReminderEntity): Long {
        return reminderDao.insertReminder(reminder)
    }

    override suspend fun getAllReminder(currentTime: Long): List<ReminderEntity> {
        return reminderDao.getAllPendingReminder(currentTime)
    }

    override suspend fun getAllCompletedReminder(currentTime: Long): List<ReminderEntity> {
        return reminderDao.getAllCompletedReminder(currentTime)
    }

    override suspend fun deleteReminder(id: Int) {
        reminderDao.deleteReminder(ReminderEntity(id = id))
    }
}