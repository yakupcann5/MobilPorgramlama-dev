package com.mobilProgramlama.odev.domain.model.reminder

import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity

data class ReminderModel(
    var id: Int? = null,
    var title: String,
    var description: String,
    var date: Long?,
    var sound: String?,
    var time: Long?,
    var timer: String?,
    var isDone: Boolean?,
    var isFavorite: Boolean?,
): java.io.Serializable {
    fun toReminderEntity(): ReminderEntity {
        return ReminderEntity(
            id = id,
            title = title,
            description = description,
            date = date,
            sound = sound,
            time = time,
            timer = timer,
            isDone = isDone,
            isFavorite = isFavorite
        )
    }
}