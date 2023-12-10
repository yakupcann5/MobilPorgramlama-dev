package com.mobilProgramlama.odev.data.locale.dao.reminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

@Dao
interface ReminderDao {
    @Insert
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminder(): List<ReminderEntity>

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)
}