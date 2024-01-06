package com.mobilProgramlama.odev.domain.use_case.reminder

import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.model.reminder.ReminderModel
import com.mobilProgramlama.odev.domain.repository.reminder.ReminderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(reminder: ReminderModel) = flow {
        try {
            emit(RequestState.Loading())
            val result = reminderRepository.insertReminder(reminder.toReminderEntity())
            emit(RequestState.Success(result))
        } catch (e: Exception) {
            emit(
                RequestState.Error(
                    e.localizedMessage ?: "An unexpected error occurred. Please try again later!"
                )
            )
        }

    }.catch {
        emit(
            RequestState.Error(
                it.localizedMessage ?: "An unexpected error occurred. Please try again later!"
            )
        )
    }
}