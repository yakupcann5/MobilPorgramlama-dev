package com.mobilProgramlama.odev.ui.reminder_add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobilProgramlama.odev.data.locale.entity.reminder.ReminderEntity
import com.mobilProgramlama.odev.util.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderAddViewModel @Inject constructor(
    private val preferences: MyPreferences,
) : ViewModel() {
    val checkInput = MutableLiveData<Boolean>()
    val checkInsertReminderResult = MutableLiveData<Boolean>()
    val addReminderModelDate = MutableLiveData<String>()
    val addReminderModelTime = MutableLiveData<String>()
    val addReminderModelDescription = MutableLiveData<String>()
    val addReminderModelTimer = MutableLiveData<String>()

    fun checkInput() {
        checkInput.value =
            !addReminderModelDate.value.isNullOrEmpty()
                    && !addReminderModelTime.value.isNullOrEmpty()
                    && !addReminderModelDescription.value.isNullOrEmpty()
                    && !addReminderModelTimer.value.isNullOrEmpty()
    }

    fun insertReminder() {
        val reminderEntity = ReminderEntity(
            date = null,
            time = null,
            description = "test",
            timer = "10",
            isDone = false,
            isFavorite = false
        )
        /**usecase invoke edilecek
         * ReminderEntity'nin parametreleri databinding ile alınacak
         * */
    }
}