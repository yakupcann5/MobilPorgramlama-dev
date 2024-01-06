package com.mobilProgramlama.odev.ui.reminder_add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobilProgramlama.odev.common.Constants
import com.mobilProgramlama.odev.common.RequestState
import com.mobilProgramlama.odev.common.Utils
import com.mobilProgramlama.odev.domain.model.reminder.ReminderModel
import com.mobilProgramlama.odev.domain.use_case.reminder.SaveReminderUseCase
import com.mobilProgramlama.odev.ui.BaseViewModel
import com.mobilProgramlama.odev.util.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class ReminderAddViewModel @Inject constructor(
    application: Application,
    private val preferences: MyPreferences,
    private val saveReminderUseCase: SaveReminderUseCase
) : BaseViewModel(application) {
    val checkInput = MutableLiveData<Boolean>()
    val checkInsertReminderResult = MutableLiveData<Boolean>()
    val addReminderModelTitle = MutableLiveData<String>()
    val addReminderModelDate = MutableLiveData<String>()
    val addReminderModelTime = MutableLiveData<String>()
    val addReminderModelDescription = MutableLiveData<String>()
    val addReminderModelTimer = MutableLiveData<String>()
    val selectedSoundUri = MutableLiveData<String>()

    fun checkInput() {
        checkInput.value =
            !addReminderModelTitle.value.isNullOrEmpty()
                    && !addReminderModelDate.value.isNullOrEmpty()
                    && !addReminderModelTime.value.isNullOrEmpty()
                    && !addReminderModelDescription.value.isNullOrEmpty()
                    && !addReminderModelTimer.value.isNullOrEmpty()
    }

    fun insertReminder(sound: String?) {
        val dateTimeFormat =
            SimpleDateFormat("${Constants.DEFAULT_DATE_FORMAT} ${Constants.DEFAULT_TIME_FORMAT}")
        val reminderDateTime =
            dateTimeFormat.parse("${addReminderModelDate.value} ${addReminderModelTime.value}")
        val reminderModel = ReminderModel(
            title = addReminderModelTitle.value!!,
            description = addReminderModelDescription.value ?: "",
            date = reminderDateTime?.time ?: 0,
            sound = sound ?: "",
            time = reminderDateTime?.time ?: 0,
            timer = addReminderModelTimer.value ?: "",
            isDone = false,
            isFavorite = false
        )
        saveReminderUseCase.invoke(reminderModel).onEach {
            when (it) {
                is RequestState.Success -> {
                    checkInsertReminderResult.value = true
                }
                is RequestState.Error -> {
                    checkInsertReminderResult.value = false
                }
                is RequestState.Loading -> {
                    checkInsertReminderResult.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}