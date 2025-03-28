package com.example.formvalidation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.formvalidation.MainActivity2
import com.example.formvalidation.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etUserId = findViewById<EditText>(R.id.etUserId)
        val btnValidate = findViewById<Button>(R.id.btnValidate)

        btnValidate.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val userId = etUserId.text.toString().trim()

            // Validation checks
            if (username.isEmpty() || userId.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (!username.matches(Regex("^[a-zA-Z]+$"))) {
                Toast.makeText(this, "Username should contain only alphabets", Toast.LENGTH_SHORT).show()
            } else if (!userId.matches(Regex("^[0-9]{4}$"))) {
                Toast.makeText(this, "User ID must be a 4-digit number", Toast.LENGTH_SHORT).show()
            } else {
                // Successful validation
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("USERNAME", username)
                intent.putExtra("USERID", userId)
                startActivity(intent)
            }
        }
    }
}
