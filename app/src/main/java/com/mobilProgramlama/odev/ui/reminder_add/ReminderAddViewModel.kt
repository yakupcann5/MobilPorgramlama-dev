package com.mobilProgramlama.odev.ui.reminder_add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobilProgramlama.odev.util.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderAddViewModel @Inject constructor(
    private val preferences: MyPreferences,
    ): ViewModel() {
    val addReminderModelDate = MutableLiveData<String>()
    val addReminderModelTime = MutableLiveData<String>()
}