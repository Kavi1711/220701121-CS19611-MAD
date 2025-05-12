package com.example.productivity_pocket

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productivity_pocket.adapters.NotesAdapter
import com.example.productivity_pocket.R
import java.util.*

class TextToSpeechActivity : AppCompatActivity(), OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        // Initialize Text-to-Speech
        tts = TextToSpeech(this, this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize NotesAdapter (Ensure you have a list of notes to pass to the adapter)
        val notesList = listOf("Note 1", "Note 2", "Note 3") // Sample data
        notesAdapter = NotesAdapter(notesList) { note ->
            // Handle note click to read aloud
            speakOut(note)
        }
        recyclerView.adapter = notesAdapter
    }

    private fun speakOut(text: String) {
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val langResult = tts.setLanguage(Locale.US)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle missing language data or unsupported language
            }
        } else {
            // Handle initialization failure
        }
    }

    override fun onDestroy() {
        // Cleanup TTS when the activity is destroyed
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
