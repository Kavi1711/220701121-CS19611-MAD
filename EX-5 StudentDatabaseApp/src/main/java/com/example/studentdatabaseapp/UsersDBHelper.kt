package com.example.studentdatabaseapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = "CREATE TABLE students (" +
                "register_number TEXT PRIMARY KEY, " +
                "name TEXT, " +
                "cgpa REAL)"
        db.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun insertStudent(student: UserModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("register_number", student.registerNumber)
            put("name", student.name)
            put("cgpa", student.cgpa)
        }
        return db.insert("students", null, values) != -1L
    }

    fun getStudent(registerNumber: String): UserModel? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students WHERE register_number=?", arrayOf(registerNumber))
        return if (cursor.moveToFirst()) {
            val student = UserModel(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getDouble(2)
            )
            cursor.close()
            student
        } else {
            cursor.close()
            null
        }
    }
    fun updateStudent(student: UserModel): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("cgpa", student.cgpa)
        }
        return db.update("students", values, "register_number=?", arrayOf(student.registerNumber)) > 0
    }


    fun deleteStudent(registerNumber: String): Boolean {
        val db = writableDatabase
        return db.delete("students", "register_number=?", arrayOf(registerNumber)) > 0
    }

    fun getAllStudents(): List<UserModel> {
        val students = mutableListOf<UserModel>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)
        while (cursor.moveToNext()) {
            students.add(UserModel(cursor.getString(0), cursor.getString(1), cursor.getDouble(2)))
        }
        cursor.close()
        return students
    }

    companion object {
        private const val DATABASE_NAME = "StudentDB"
        private const val DATABASE_VERSION = 1
    }
}
