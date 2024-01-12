package com.mobilProgramlama.odev.ui.reminder

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mobilProgramlama.odev.common.Utils
import com.mobilProgramlama.odev.databinding.ActivityReminderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityReminderBinding
    private val reminderViewModel: ReminderViewModel by viewModels()
    private lateinit var mp: MediaPlayer

    // SensorManager ve Sensor nesnelerini başlat
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private val shakeThreshold = 800 // Bu değer, sallama eşiğini belirler

    // Son güncelleme zamanı ve son x, y, z değerleri

    private var lastUpdate: Long = 0
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private var lastZ: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        reminderViewModel.getAllReminder(System.currentTimeMillis())
        getAllReminder()

        // SensorManager ve Sensor nesnelerini başlatma
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    // Sensor olaylarını dinleyen listener

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // Do nothing
        }

        override fun onSensorChanged(event: SensorEvent) {
            // Sensörün değeri değiştiğinde çağrılır

            // Sensör değerlerini al
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Geçerli zamanı al
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastUpdate) > 100) {
                val diffTime = currentTime - lastUpdate
                lastUpdate = currentTime

                // Hızı hesapla
                val speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                // Eğer hız, sallama eşiğinden büyükse, alarmı durdur
                if (speed > shakeThreshold) {
                    // Telefon sallandı, alarmı durdur
                    mp.stop()
                }

                // Son x, y, z değerlerini güncelle
                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }


    private fun getAllReminder() {
        reminderViewModel.allReminder.observe(this) {
            it.let {
                if (it != null) {
                    mp = MediaPlayer.create(this, Uri.parse(it.sound))
                    binding.descriptionTextView.text = it.description
                    binding.titleTextView.text = it.title
                    binding.timeTextView.text = Utils.getDateStringByTimestampTime(it.time!!)
                    binding.dateTextView.text = Utils.getDateStringByTimestampDate(it.date!!)
                    mp.start()
                }
            }
        }
    }

    private fun init(){
        binding.customToolbar.customToolbarOptionalButton.visibility = View.GONE
        binding.customToolbar.customToolbarTitle.text = "Hatırlatma"
        binding.reminderAddButton2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.reminderAddButton2.id -> {
                finishAffinity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { accelerometer ->
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        //sensörü devre dışı bırak
        sensorManager.unregisterListener(sensorEventListener)
    }
}