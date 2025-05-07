package com.example.emailsenderapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var toEditText: EditText
    private lateinit var subjectEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toEditText = findViewById(R.id.editTextTo)
        subjectEditText = findViewById(R.id.editTextSubject)
        messageEditText = findViewById(R.id.editTextMessage)
        sendButton = findViewById(R.id.buttonSend)

        sendButton.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val recipient = toEditText.text.toString()
        val subject = subjectEditText.text.toString()
        val message = messageEditText.text.toString()

        if (recipient.isNotEmpty() && subject.isNotEmpty() && message.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // ensures only email apps handle this
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }

            try {
                startActivity(Intent.createChooser(intent, "Send Email"))
            } catch (e: Exception) {
                Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
