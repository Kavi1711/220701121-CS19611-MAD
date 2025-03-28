package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        val buttons = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnPlus to "+", R.id.btnMinus to "-",
            R.id.btnMultiply to "*", R.id.btnDivide to "/",
            R.id.btnOpenBracket to "(", R.id.btnCloseBracket to ")",
            R.id.btnDot to "."
        )

        // Assign click listeners to number & operator buttons
        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendToExpression(value) }
        }

        // Clear Button
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        // Delete Button
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val text = tvExpression.text.toString()
            if (text.isNotEmpty()) {
                tvExpression.text = text.dropLast(1)
            }
        }

        // Equal Button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val result = evaluateExpression(tvExpression.text.toString())
                tvResult.text = result.toString()
            } catch (e: Exception) {
                tvResult.text = "Error"
            }
        }
    }

    private fun appendToExpression(value: String) {
        tvExpression.append(value)
    }

    // ✅ Function to Evaluate Mathematical Expressions Without Using `ScriptEngineManager`
    private fun evaluateExpression(expression: String): Double {
        return try {
            val postfix = infixToPostfix(expression)
            evaluatePostfix(postfix)
        } catch (e: Exception) {
            0.0
        }
    }

    // ✅ Function to Convert Infix Expression to Postfix (Reverse Polish Notation)
    private fun infixToPostfix(expression: String): List<String> {
        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)
        val output = mutableListOf<String>()
        val operators = Stack<Char>()
        var number = ""

        for (char in expression) {
            when {
                char.isDigit() || char == '.' -> number += char
                char in precedence -> {
                    if (number.isNotEmpty()) {
                        output.add(number)
                        number = ""
                    }
                    while (operators.isNotEmpty() && precedence[char]!! <= precedence[operators.peek()]!!) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(char)
                }
                char == '(' -> operators.push(char)
                char == ')' -> {
                    if (number.isNotEmpty()) {
                        output.add(number)
                        number = ""
                    }
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    operators.pop() // Remove '('
                }
            }
        }

        if (number.isNotEmpty()) {
            output.add(number)
        }
        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }

        return output
    }

    // ✅ Function to Evaluate Postfix Expression
    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()

        for (token in postfix) {
            when (token) {
                "+" -> stack.push(stack.pop() + stack.pop())
                "-" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a - b)
                }
                "*" -> stack.push(stack.pop() * stack.pop())
                "/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a / b)
                }
                else -> stack.push(token.toDouble())
            }
        }

        return if (stack.isNotEmpty()) stack.pop() else 0.0
    }
}
