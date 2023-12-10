package com.mobilProgramlama.odev.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobilProgramlama.odev.data.locale.dao.reminder.ReminderDao
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}