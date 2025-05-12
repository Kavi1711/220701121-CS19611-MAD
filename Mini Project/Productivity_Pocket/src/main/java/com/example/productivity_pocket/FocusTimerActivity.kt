package com.example.productivity_pocket

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.productivity_pocket.R

class FocusTimerActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var sessionProgressBar: ProgressBar
    private var isSessionActive = false
    private var isBreak = false
    private var focusTime: Long = 25 * 60 * 1000  // 25 minutes
    private var breakTime: Long = 5 * 60 * 1000  // 5 minutes

    private var countDownTimer: CountDownTimer? = null
    private var timeLeft: Long = focusTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_timer)

        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)
        sessionProgressBar = findViewById(R.id.sessionProgressBar)

        startButton.setOnClickListener { startTimer() }
        resetButton.setOnClickListener { resetTimer() }

        updateTimerDisplay()
    }

    private fun startTimer() {
        if (isSessionActive) {
            stopTimer()
            startButton.text = "Start"
        } else {
            startButton.text = "Pause"
            startNewSession()
        }
    }

    private fun startNewSession() {
        timeLeft = if (isBreak) breakTime else focusTime
        sessionProgressBar.max = (timeLeft / 1000).toInt()

        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimerDisplay()
                sessionProgressBar.progress = (timeLeft / 1000).toInt()
            }

            override fun onFinish() {
                if (isBreak) {
                    Toast.makeText(this@FocusTimerActivity, "Break is over!", Toast.LENGTH_SHORT).show()
                    isBreak = false
                    startNewSession()
                } else {
                    Toast.makeText(this@FocusTimerActivity, "Session is over! Take a break.", Toast.LENGTH_SHORT).show()
                    isBreak = true
                    startNewSession()
                }
            }
        }.start()

        isSessionActive = true
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        isSessionActive = false
    }

    private fun resetTimer() {
        stopTimer()
        isBreak = false
        startButton.text = "Start"
        timeLeft = focusTime
        updateTimerDisplay()
    }

    private fun updateTimerDisplay() {
        val minutes = (timeLeft / 1000) / 60
        val seconds = (timeLeft / 1000) % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }
}
