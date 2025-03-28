package com.example.studentdetailsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the fragments into the FrameLayouts
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragmentContainer1, StudentBasicDetailsFragment())
        transaction.replace(R.id.fragmentContainer2, StudentMarkDetailsFragment())

        transaction.commit()
    }
}
