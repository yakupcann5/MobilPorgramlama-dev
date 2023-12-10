package com.mobilProgramlama.odev.data.locale.entity.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    var id: Int? = 0,
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("date")
    var date: String? = "",
    @SerializedName("time")
    var time: String? = "",
    @SerializedName("is_done")
    var isDone: Boolean = false,
    @SerializedName("is_favorite")
    var isFavorite: Boolean = false,
)