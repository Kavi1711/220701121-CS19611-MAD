package com.example.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var btnSetAlarm: Button
    private lateinit var btnStopAlarm: Button
    private lateinit var alarmListView: ListView
    private lateinit var tvTime: TextView

    private val alarms = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI Elements
        timePicker = findViewById(R.id.timePicker)
        btnSetAlarm = findViewById(R.id.btnSetAlarm)
        btnStopAlarm = findViewById(R.id.btnStopAlarm)
        alarmListView = findViewById(R.id.alarmListView)
        tvTime = findViewById(R.id.tvTime)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alarms)
        alarmListView.adapter = adapter

        // Set Alarm Button Click
        btnSetAlarm.setOnClickListener {
            setAlarm()
        }

        // Stop Alarm Button Click
        btnStopAlarm.setOnClickListener {
            stopAlarm()
        }

        // Remove alarm from list when clicked
        alarmListView.setOnItemClickListener { _, _, position, _ ->
            alarms.removeAt(position)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timePicker.hour)
            set(Calendar.MINUTE, timePicker.minute)
            set(Calendar.SECOND, 0)
        }

        // Set alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Update UI
        val alarmTime = String.format("%02d:%02d %s",
            if (timePicker.hour > 12) timePicker.hour - 12 else timePicker.hour,
            timePicker.minute,
            if (timePicker.hour >= 12) "PM" else "AM"
        )

        tvTime.text = alarmTime
        alarms.add("Alarm set for $alarmTime")
        adapter.notifyDataSetChanged()

        Toast.makeText(this, "Alarm Set for $alarmTime", Toast.LENGTH_SHORT).show()
    }

    private fun stopAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)

        if (alarms.isNotEmpty()) {
            alarms.clear()
            adapter.notifyDataSetChanged()
        }

        Toast.makeText(this, "Alarm Stopped", Toast.LENGTH_SHORT).show()
    }
}
