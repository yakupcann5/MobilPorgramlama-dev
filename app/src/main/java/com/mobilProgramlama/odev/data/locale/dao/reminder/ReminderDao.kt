package com.mobilProgramlama.odev.data.locale.dao.reminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminder(): List<ReminderEntity>

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)
}