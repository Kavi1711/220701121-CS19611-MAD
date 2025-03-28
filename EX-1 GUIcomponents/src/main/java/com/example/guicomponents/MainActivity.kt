package com.example.guicomponents

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var fontSize = 18f  // Initial font size
    private val colors = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK)
    private var colorIndex = 0
    private val bgColors = arrayOf(Color.YELLOW, Color.CYAN, Color.LTGRAY, Color.WHITE)
    private var bgIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView2 = findViewById<TextView>(R.id.textView2)
        val btnFontSize = findViewById<Button>(R.id.button)
        val btnFontColor = findViewById<Button>(R.id.button2)
        val btnBgColor = findViewById<Button>(R.id.button3)
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)  // Get the root layout

        // Change Font Size
        btnFontSize.setOnClickListener {
            fontSize += 2
            if (fontSize > 50f) fontSize = 18f  // Reset if too large
            textView2.textSize = fontSize
        }

        // Change Font Color
        btnFontColor.setOnClickListener {
            textView2.setTextColor(colors[colorIndex])
            colorIndex = (colorIndex + 1) % colors.size
        }

        // Change Background Color (Entire Screen)
        btnBgColor.setOnClickListener {
            mainLayout.setBackgroundColor(bgColors[bgIndex])
            bgIndex = (bgIndex + 1) % bgColors.size
        }
    }
}
