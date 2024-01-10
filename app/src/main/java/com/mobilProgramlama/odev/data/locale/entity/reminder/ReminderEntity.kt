package com.mobilProgramlama.odev.data.locale.entity.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var date: Long? = null,
    var sound: String? = null,
    var time: Long? = null,
    var timer: String? = null,
    var isDone: Boolean? = null,
    var isFavorite: Boolean? = null,
)