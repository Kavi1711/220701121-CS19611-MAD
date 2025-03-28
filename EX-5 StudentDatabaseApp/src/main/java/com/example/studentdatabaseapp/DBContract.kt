package com.example.studentdatabaseapp

import android.provider.BaseColumns

object DBContract {
    object StudentEntry : BaseColumns {
        const val TABLE_NAME = "students"
        const val COLUMN_REGISTER_NUMBER = "register_number"
        const val COLUMN_NAME = "name"
        const val COLUMN_CGPA = "cgpa"
    }
}
