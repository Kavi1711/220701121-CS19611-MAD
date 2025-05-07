package com.example.studentdatasaver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var etRegisterNumber: EditText
    private lateinit var etName: EditText
    private lateinit var etCgpa: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLoad: Button
    private lateinit var tvOutput: TextView

    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etRegisterNumber = findViewById(R.id.etRegisterNumber)
        etName = findViewById(R.id.etName)
        etCgpa = findViewById(R.id.etCgpa)
        btnSave = findViewById(R.id.btnSave)
        btnLoad = findViewById(R.id.btnLoad)
        tvOutput = findViewById(R.id.tvOutput)

        // Request Storage Permission
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)

        btnSave.setOnClickListener {
            val regNumber = etRegisterNumber.text.toString()
            val name = etName.text.toString()
            val cgpa = etCgpa.text.toString()

            if (regNumber.isNotEmpty() && name.isNotEmpty() && cgpa.isNotEmpty()) {
                saveToFile(regNumber, name, cgpa)
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoad.setOnClickListener {
            loadFromFile()
        }
    }

    // Function to request permission
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    // Function to save data
    private fun saveToFile(registerNumber: String, name: String, cgpa: String) {
        val folder = File(getExternalFilesDir(null), "StudentData")
        if (!folder.exists()) folder.mkdirs()

        val file = File(folder, "StudentDetails.txt")
        try {
            file.appendText("Register Number: $registerNumber, Name: $name, CGPA: $cgpa\n")
            Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Function to load data
    private fun loadFromFile() {
        val file = File(getExternalFilesDir(null), "StudentData/StudentDetails.txt")
        if (file.exists()) {
            val data = file.readText()
            tvOutput.text = data
        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_LONG).show()
        }
    }
}
