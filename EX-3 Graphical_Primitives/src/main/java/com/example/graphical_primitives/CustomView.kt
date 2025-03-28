package com.example.shapedrawerapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        textSize = 50f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw a circle
        canvas.drawCircle(200f, 200f, 150f, paint)

        // Draw a rectangle
        paint.color = Color.RED
        canvas.drawRect(400f, 100f, 700f, 400f, paint)

        // Draw an ellipse
        paint.color = Color.GREEN
        canvas.drawOval(100f, 500f, 600f, 800f, paint)

        // Draw text
        paint.color = Color.BLACK
        canvas.drawText("Hello, World!", 300f, 1000f, paint)
    }
}