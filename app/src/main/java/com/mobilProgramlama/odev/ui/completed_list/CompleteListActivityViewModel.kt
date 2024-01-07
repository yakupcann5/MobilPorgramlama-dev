package com.mobilProgramlama.odev.ui.completed_list

import android.app.Application
import com.mobilProgramlama.odev.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompleteListActivityViewModel @Inject constructor(
    application: Application
): BaseViewModel(application = application) {


    fun getAllCompleteReminderList() {

    }
}