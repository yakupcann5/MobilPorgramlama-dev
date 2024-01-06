package com.mobilProgramlama.odev.data.locale.entity.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String?,
    var description: String?,
    var date: Long?,
    var sound: String?,
    var time: Long?,
    var timer: String?,
    var isDone: Boolean?,
    var isFavorite: Boolean?,
)