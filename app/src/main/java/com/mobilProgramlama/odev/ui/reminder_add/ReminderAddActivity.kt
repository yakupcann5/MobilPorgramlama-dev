package com.mobilProgramlama.odev.ui.reminder_add

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.mobilProgramlama.odev.common.Timer
import com.mobilProgramlama.odev.databinding.ActivityReminderAddBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@AndroidEntryPoint
class ReminderAddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReminderAddBinding
    private val pickRingtone =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri =
                    result.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                // uri'yi veritabanına kaydet
                val ringtone = RingtoneManager.getRingtone(this, uri)
                val name = ringtone.getTitle(this)
                binding.soundInput.setText(name)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddBinding.inflate(layoutInflater)
        setTimerDropDown()
        initView()
        setContentView(binding.root)
    }

    private fun setTimerDropDown() {
        val timerList = arrayListOf<String>()
        Timer.entries.forEach {
            timerList.add(it.type)
        }
        binding.timerInput.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                timerList
            )
        )
    }

    private fun initView() {
        binding.saveButton.setOnClickListener(this)
        binding.dateInput.setOnClickListener(this)
        binding.timeInput.setOnClickListener(this)
        binding.descriptionInput.setOnClickListener(this)
        binding.soundInput.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.saveButton.id -> {
                addReminder()
            }

            binding.dateInput.id -> {
                createDatePicker(binding.dateInput)
            }

            binding.timeInput.id -> {
                createTimePicker(binding.timeInput)
            }

            binding.descriptionInput.id -> {
                binding.descriptionInput.requestFocus()
            }

            binding.soundInput.id -> {
                openRingtonePicker()
            }
        }
    }

    private fun openRingtonePicker() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone")
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        )
        pickRingtone.launch(intent)
    }

    private fun createDatePicker(view: TextInputEditText) {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        val start = calendar.timeInMillis
        calendar.add(
            Calendar.YEAR,
            5
        )
        val end = calendar.timeInMillis
        val constraints = CalendarConstraints.Builder()
            .setStart(start)
            .setEnd(end)
            .setValidator(DateValidatorPointForward.from(today))
            .build()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tarih Seçiniz")
            .setCalendarConstraints(constraints)
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        setDatePickerListener(picker, view)
        picker.show(supportFragmentManager, "date_picker")
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDatePickerListener(picker: MaterialDatePicker<Long>, view: TextInputEditText) {
        picker.addOnPositiveButtonClickListener {
            view.setText(
                SimpleDateFormat("dd/MM/yyyy").format(
                    Date(it)
                )
            )
        }
    }

    private fun createTimePicker(v: TextInputEditText) {
        val cal = Calendar.getInstance()
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val time = SimpleDateFormat("HH:mm").format(cal.time).toString()
                v.setText(time)
            }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun addReminder() {
        //viewModel.checkInput()
        //viewModel.insertReminder()
        //viewModel.checkInsertReminderResult()
    }
}