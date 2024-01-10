package com.mobilProgramlama.odev.ui.completed_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.domain.use_case.reminder.GetCompleteReminderUseCase
import com.mobilProgramlama.odev.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CompleteListActivityViewModel @Inject constructor(
    application: Application,
    private val getCompleteReminderUseCase: GetCompleteReminderUseCase
): BaseViewModel(application = application) {
    val completeList = MutableLiveData<List<ReminderEntity>?>()

    fun getAllCompleteReminderList(currentTime: Long) {
        getCompleteReminderUseCase.invoke(currentTime).onEach {result->
            when (result) {
                is RequestState.Success -> {
                    completeList.value = result.data
                }
                is RequestState.Error -> {
                    Log.d("Reminder", "getAllReminder: ${result.errorCode}")
                }
                is RequestState.Loading -> {
                    Log.d("Reminder", "getAllReminder: Loading")
                }
            }
        }.launchIn(viewModelScope)
    }
}