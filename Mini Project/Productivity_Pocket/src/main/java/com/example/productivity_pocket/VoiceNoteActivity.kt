package com.example.productivity_pocket
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class VoiceNoteActivity : AppCompatActivity() {

    private lateinit var startVoiceBtn: Button
    private lateinit var saveNoteBtn: Button
    private lateinit var voiceNoteText: TextView
    private var recognizedText: String = ""

    // Activity Result Launcher for Speech Recognition
    private val voiceInputLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                recognizedText = matches[0]
                voiceNoteText.text = recognizedText
            } else {
                Toast.makeText(this, "No voice input recognized", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Voice input cancelled or failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_note)

        startVoiceBtn = findViewById(R.id.startVoiceBtn)
        saveNoteBtn = findViewById(R.id.saveNoteBtn)
        voiceNoteText = findViewById(R.id.voiceNoteText)

        startVoiceBtn.setOnClickListener {
            startVoiceInput()
        }

        saveNoteBtn.setOnClickListener {
            saveVoiceNote(recognizedText)
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your note")
        }
        voiceInputLauncher.launch(intent)
    }

    private fun saveVoiceNote(note: String) {
        if (note.isNotBlank()) {
            val sharedPref = getSharedPreferences("voice_notes", MODE_PRIVATE)
            val editor = sharedPref.edit()
            val timestamp = System.currentTimeMillis().toString()
            editor.putString("note_$timestamp", note)
            editor.apply()
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No note to save", Toast.LENGTH_SHORT).show()
        }
    }
}
