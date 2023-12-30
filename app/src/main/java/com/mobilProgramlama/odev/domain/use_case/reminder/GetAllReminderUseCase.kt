package com.mobilProgramlama.odev.domain.use_case.reminder

import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.domain.repository.reminder.ReminderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllReminderUseCase @Inject constructor(private val reminderRepository: ReminderRepository) {
    operator fun invoke() = flow {
        try {
            emit(RequestState.Loading())
            val reminderList = reminderRepository.getAllReminder()
            emit(RequestState.Success(reminderList))
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