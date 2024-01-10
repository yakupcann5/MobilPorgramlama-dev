package com.mobilProgramlama.odev.ui.reminder

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.use_case.reminder.GetAllReminderUseCase
import com.mobilProgramlama.odev.domain.use_case.reminder.GetCompleteSingleReminderUseCase
import com.mobilProgramlama.odev.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    application: Application,
    private val getCompleteReminderUseCase: GetCompleteSingleReminderUseCase

) : BaseViewModel(application) {

    val allReminder: MutableLiveData<ReminderEntity?> = MutableLiveData()

    fun getAllReminder(currentTime: Long) {
        getCompleteReminderUseCase.invoke(currentTime).onEach {
            when (it) {
                is RequestState.Success -> {
                    allReminder.value = it.data
                }

                is RequestState.Error -> {
                    Log.d("Reminder", "getAllReminder: ${it.errorCode}")
                }

                is RequestState.Loading -> {
                    Log.d("Reminder", "getAllReminder: Loading")
                }
            }
        }.launchIn(viewModelScope)
    }
}