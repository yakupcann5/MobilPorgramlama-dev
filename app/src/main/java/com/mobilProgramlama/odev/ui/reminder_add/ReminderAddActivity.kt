package com.mobilProgramlama.odev.ui.reminder_add

import android.Manifest.permission.RECEIVE_BOOT_COMPLETED
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.mobilProgramlama.odev.common.AlarmReceiver
import com.mobilProgramlama.odev.common.Constants
import com.mobilProgramlama.odev.common.Timer
import com.mobilProgramlama.odev.databinding.ActivityReminderAddBinding
import com.mobilProgramlama.odev.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@AndroidEntryPoint
class ReminderAddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReminderAddBinding
    private val reminderAddViewModel: ReminderAddViewModel by viewModels()
    private val pickRingtone =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri =
                    result.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                reminderAddViewModel.selectedSoundUri.value = uri.toString()
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
        binding.viewmodel = reminderAddViewModel
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
                reminderAddViewModel.checkInsertReminderResult.observe(this) {
                    if (it) {
                        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                        val alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
                            intent.action = RECEIVE_BOOT_COMPLETED
                            val uniqueId = getUniqueReminder()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                                PendingIntent.getBroadcast(
                                    this,
                                    uniqueId,
                                    intent,
                                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                                )
                            else
                                PendingIntent.getBroadcast(
                                    this,
                                    uniqueId,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                                )
                        }

                        val dateTimeFormat =
                            SimpleDateFormat("${Constants.DEFAULT_DATE_FORMAT} ${Constants.DEFAULT_TIME_FORMAT}")
                        val reminderDateTime =
                            dateTimeFormat.parse("${reminderAddViewModel.addReminderModelDate.value} ${reminderAddViewModel.addReminderModelTime.value}")

                        Log.d("muhammed", "addReminder: ${reminderDateTime.time}")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                            alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                reminderDateTime.time,
                                alarmIntent
                            )
                        } else {
                            alarmManager.setExact(
                                AlarmManager.RTC_WAKEUP,
                                reminderDateTime.time,
                                alarmIntent
                            )
                        }

                        Toast.makeText(this, "Hatırlatıcı Oluşturuldu.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Bir Sorun Oluştu. Lütfen Tekrar Deneyin.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
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

    private fun getUniqueReminder(): Int {
        val sharedPreferences =
            getSharedPreferences("MyApp", MODE_PRIVATE)
        val uniqueId =
            sharedPreferences.getInt("ReminderId", 0)
        sharedPreferences.edit()
            .putInt("ReminderId", uniqueId + 1).apply()
        return uniqueId
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
                SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT).format(
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
                val time =
                    SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT).format(cal.time).toString()
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
        reminderAddViewModel.checkInput()
        reminderAddViewModel.checkInput.observe(this) { notEmpty ->
            if (notEmpty) {
                reminderAddViewModel.selectedSoundUri.observe(this) {
                    reminderAddViewModel.insertReminder(it)
                    /*if (it.isNullOrEmpty()) {
                        reminderAddViewModel.insertReminder(null)
                    } else {
                        reminderAddViewModel.insertReminder(it)
                    }*/
                }
            }
        }
    }
    /*
      fun deneme() {
          reminderAddViewModel.checkInput()
          reminderAddViewModel.checkInput.observe(this) {
              reminderAddViewModel.insertReminder()
          }
      }*/
}