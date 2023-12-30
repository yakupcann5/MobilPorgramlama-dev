package com.mobilProgramlama.odev.data.locale.entity.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("date")
    var date: Long? = null,
    @SerializedName("sound")
    var sound: String? = null,
    @SerializedName("time")
    var time: Long? = null,
    @SerializedName("timer")
    var timer: String? = "",
    @SerializedName("is_done")
    var isDone: Boolean = false,
    @SerializedName("is_favorite")
    var isFavorite: Boolean = false,
)