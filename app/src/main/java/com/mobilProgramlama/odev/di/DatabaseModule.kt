package com.mobilProgramlama.odev.di

import android.content.Context
import androidx.room.Room
import com.mobilProgramlama.odev.data.locale.AppDatabase
import com.mobilProgramlama.odev.data.locale.dao.reminder.ReminderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "mobil_programlama_ödev")
            .build()
    }
    @Provides
    fun provideReminderDao(appDatabase: AppDatabase): ReminderDao {
        return appDatabase.reminderDao()
    }
}