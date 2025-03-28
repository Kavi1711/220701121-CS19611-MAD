package com.example.studentdatabaseapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UsersDBHelper
    private lateinit var regNoInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var cgpaInput: EditText
    private lateinit var addButton: Button
    private lateinit var viewButton: Button
    private lateinit var modifyButton: Button
    private lateinit var deleteButton: Button
    private lateinit var clearButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UsersDBHelper(this)

        regNoInput = findViewById(R.id.editTextRegNo)
        nameInput = findViewById(R.id.editTextName)
        cgpaInput = findViewById(R.id.editTextCgpa)
        addButton = findViewById(R.id.buttonAdd)
        viewButton = findViewById(R.id.buttonView)
        modifyButton = findViewById(R.id.buttonModify)
        deleteButton = findViewById(R.id.buttonDelete)
        clearButton = findViewById(R.id.buttonClear)
        resultTextView = findViewById(R.id.textViewResult)

        addButton.setOnClickListener { addStudent() }
        viewButton.setOnClickListener { viewStudents() }
        modifyButton.setOnClickListener { showModifyDialog() }
        deleteButton.setOnClickListener { showDeleteDialog() }
        clearButton.setOnClickListener { clearFields() }
    }

    private fun addStudent() {
        val regNo = regNoInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val cgpa = cgpaInput.text.toString().toDoubleOrNull()

        if (regNo.isEmpty() || name.isEmpty() || cgpa == null) {
            showToast("Please enter all details correctly")
            return
        }

        val student = UserModel(regNo, name, cgpa)
        val success = dbHelper.insertStudent(student)

        if (success) {
            showToast("Student added successfully")
            clearFields()
        } else {
            showToast("Error: Student with this Register Number already exists!")
        }
    }

    private fun viewStudents() {
        val students = dbHelper.getAllStudents()
        if (students.isEmpty()) {
            resultTextView.text = "No students found."
        } else {
            val resultText = StringBuilder()
            for (student in students) {
                resultText.append("Reg No: ${student.registerNumber}, Name: ${student.name}, CGPA: ${student.cgpa}\n")
            }
            resultTextView.text = resultText.toString()
        }
    }

    private fun showModifyDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enter Roll No to Modify")

        val input = EditText(this)
        dialog.setView(input)

        dialog.setPositiveButton("OK") { _, _ ->
            val regNo = input.text.toString().trim()
            if (regNo.isNotEmpty()) {
                modifyStudent(regNo)
            } else {
                showToast("Roll No cannot be empty")
            }
        }
        dialog.setNegativeButton("Cancel", null)
        dialog.show()
    }

    private fun modifyStudent(regNo: String) {
        val existingStudent = dbHelper.getStudent(regNo)
        if (existingStudent == null) {
            showToast("Student not found!")
            return
        }

        regNoInput.setText(existingStudent.registerNumber)
        nameInput.setText(existingStudent.name)
        cgpaInput.setText(existingStudent.cgpa.toString())

        addButton.text = "Update"
        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val cgpa = cgpaInput.text.toString().toDoubleOrNull()

            if (name.isEmpty() || cgpa == null) {
                showToast("Please enter valid details")
                return@setOnClickListener
            }

            val updatedStudent = UserModel(regNo, name, cgpa)
            val success = dbHelper.updateStudent(updatedStudent)

            if (success) {
                showToast("Student details updated successfully")
                clearFields()
                addButton.text = "Add"
                addButton.setOnClickListener { addStudent() }
            } else {
                showToast("Error updating student")
            }
        }
    }

    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enter Roll No to Delete")

        val input = EditText(this)
        dialog.setView(input)

        dialog.setPositiveButton("OK") { _, _ ->
            val regNo = input.text.toString().trim()
            if (regNo.isNotEmpty()) {
                deleteStudent(regNo)
            } else {
                showToast("Roll No cannot be empty")
            }
        }
        dialog.setNegativeButton("Cancel", null)
        dialog.show()
    }

    private fun deleteStudent(regNo: String) {
        val success = dbHelper.deleteStudent(regNo)
        if (success) {
            showToast("Student deleted successfully")
            clearFields()
        } else {
            showToast("Error: Student not found!")
        }
    }


    private fun clearFields() {
        regNoInput.text.clear()
        nameInput.text.clear()
        cgpaInput.text.clear()
        resultTextView.text = ""
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
