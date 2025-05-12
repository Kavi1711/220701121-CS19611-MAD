package com.example.productivity_pocket.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.productivity_pocket.R

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_note)

        // Initialize the button using the correct ID
        btnDelete = findViewById(R.id.btnDelete)

        // Set a click listener for the delete button
        btnDelete.setOnClickListener {
            // Perform the deletion logic (e.g., remove from database, update UI)
            setResult(RESULT_OK)  // Send back the result to the calling activity
            finish()  // Close the delete activity
        }
    }
}
