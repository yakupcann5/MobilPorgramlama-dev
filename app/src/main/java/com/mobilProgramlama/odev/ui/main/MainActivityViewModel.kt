package com.mobilProgramlama.odev.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.use_case.reminder.GetAllReminderUseCase
import com.mobilProgramlama.odev.ui.BaseViewModel
import com.mobilProgramlama.odev.util.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    private val preferences: MyPreferences,
    private val getAllReminderUseCase: GetAllReminderUseCase
): BaseViewModel(application) {
    val allReminder: MutableLiveData<List<ReminderEntity>?> = MutableLiveData()

    fun getAllReminder() {
        getAllReminderUseCase.invoke().onEach {
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
