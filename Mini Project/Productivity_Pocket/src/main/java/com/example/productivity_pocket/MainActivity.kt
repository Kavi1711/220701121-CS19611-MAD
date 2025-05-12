package com.example.productivity_pocket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.productivity_pocket.R
import com.example.productivity_pocket.VoiceNoteActivity
import com.example.productivity_pocket.TextToSpeechActivity
//import com.example.productivity_pocket.activities.VoiceNoteActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnTaskManager).setOnClickListener {
            startActivity(Intent(this, TaskManagerActivity::class.java))
        }

        findViewById<Button>(R.id.btnVoiceNotes).setOnClickListener {
            startActivity(Intent(this, VoiceNoteActivity::class.java))
        }

        findViewById<Button>(R.id.btnAlarmScheduler).setOnClickListener {
            startActivity(Intent(this, AlarmSchedulerActivity::class.java))
        }

        findViewById<Button>(R.id.btnDailyJournal).setOnClickListener {
            startActivity(Intent(this, DailyJournalActivity::class.java))
        }

        findViewById<Button>(R.id.btnTextToSpeech).setOnClickListener {
            startActivity(Intent(this, TextToSpeechActivity::class.java))
        }

        findViewById<Button>(R.id.btnFocusTimer).setOnClickListener {
            startActivity(Intent(this, FocusTimerActivity::class.java))
        }

        findViewById<Button>(R.id.btnStickyNotes).setOnClickListener {
            startActivity(Intent(this, StickyNotesActivity::class.java))
        }
    }
}
