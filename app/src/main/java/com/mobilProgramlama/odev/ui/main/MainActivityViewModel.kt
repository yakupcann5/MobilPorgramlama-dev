package com.mobilProgramlama.odev.ui.main

import android.app.Application
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

    fun getAllReminder(currentTime: Long) {
        getAllReminderUseCase.invoke(currentTime).onEach {
            when (it) {
                is RequestState.Success -> {
                    allReminder.value = it.data
                }
                is RequestState.Error -> {
                }
                is RequestState.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }
}
