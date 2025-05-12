package com.example.productivity_pocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.EditText
import android.widget.Toast
import com.example.productivity_pocket.R

class StickyNotesActivity : AppCompatActivity() {

    private lateinit var stickyNotesRecyclerView: RecyclerView
    private lateinit var stickyNotesAdapter: StickyNotesAdapter
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var noteInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_notes)

        stickyNotesRecyclerView = findViewById(R.id.stickyNotesRecyclerView)
        addNoteButton = findViewById(R.id.addNoteButton)
        noteInput = findViewById(R.id.noteInput)

        stickyNotesRecyclerView.layoutManager = LinearLayoutManager(this)
        stickyNotesAdapter = StickyNotesAdapter(mutableListOf())
        stickyNotesRecyclerView.adapter = stickyNotesAdapter

        addNoteButton.setOnClickListener {
            val note = noteInput.text.toString()
            if (note.isNotEmpty()) {
                stickyNotesAdapter.addNote(note)
                noteInput.text.clear()
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
