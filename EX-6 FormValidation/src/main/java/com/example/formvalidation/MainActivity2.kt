package com.example.formvalidation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.formvalidation.R

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val username = intent.getStringExtra("USERNAME")
        val userId = intent.getStringExtra("USERID")

        tvResult.text = "Welcome, $username!\nYour ID: $userId\nValidation Successful!"
    }
}
