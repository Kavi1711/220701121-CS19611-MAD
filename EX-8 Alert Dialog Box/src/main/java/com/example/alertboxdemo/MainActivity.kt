package com.example.alertboxdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val showAlertButton = findViewById<Button>(R.id.showAlertButton)

        showAlertButton.setOnClickListener {
            val enteredText = editText.text.toString()

            val alertDialog = AlertDialog.Builder(this)
                .setTitle("MAD Lab")
                .setMessage(enteredText.ifEmpty { "No text entered!" })
                .setPositiveButton("OK") { _, _ ->
                    Toast.makeText(this, "You clicked OK", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .create()

            alertDialog.show()
        }
    }
}
