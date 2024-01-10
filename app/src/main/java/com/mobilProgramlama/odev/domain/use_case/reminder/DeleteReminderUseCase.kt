package com.mobilProgramlama.odev.domain.use_case.reminder

import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.repository.reminder.ReminderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.w3c.dom.Entity
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(reminder: ReminderEntity) = flow {
        try {
            emit(RequestState.Loading())
            val result = reminderRepository.deleteReminder(reminder.id!!)
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