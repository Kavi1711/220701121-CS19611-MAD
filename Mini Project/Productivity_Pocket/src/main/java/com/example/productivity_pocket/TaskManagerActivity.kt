package com.example.productivity_pocket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.productivity_pocket.R
import org.json.JSONArray
import org.json.JSONObject

class TaskManagerActivity : AppCompatActivity() {

    private lateinit var taskListLayout: LinearLayout
    private lateinit var editTaskInput: EditText
    private val sharedPrefs by lazy {
        getSharedPreferences("tasks", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)

        taskListLayout = findViewById(R.id.taskListLayout)
        editTaskInput = findViewById(R.id.editTaskInput)
        val addButton: Button = findViewById(R.id.btnAddTask)

        loadTasks()

        addButton.setOnClickListener {
            val taskText = editTaskInput.text.toString()
            if (taskText.isNotEmpty()) {
                addTask(taskText)
                editTaskInput.text.clear()
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTasks() {
        taskListLayout.removeAllViews()
        val tasksString = sharedPrefs.getString("task_list", "[]")
        val taskArray = JSONArray(tasksString)

        for (i in 0 until taskArray.length()) {
            val taskObject = taskArray.getJSONObject(i)
            addTaskView(taskObject.getString("text"), taskObject.getBoolean("done"))
        }
    }

    private fun addTask(taskText: String) {
        val tasksString = sharedPrefs.getString("task_list", "[]")
        val taskArray = JSONArray(tasksString)
        val newTask = JSONObject()
        newTask.put("text", taskText)
        newTask.put("done", false)
        taskArray.put(newTask)

        sharedPrefs.edit().putString("task_list", taskArray.toString()).apply()
        addTaskView(taskText, false)
    }

    private fun addTaskView(taskText: String, isDone: Boolean) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_task, taskListLayout, false)
        val checkBox = view.findViewById<CheckBox>(R.id.taskCheckbox)
        val deleteButton = view.findViewById<ImageButton>(R.id.btnDeleteTask)

        checkBox.text = taskText
        checkBox.isChecked = isDone

        checkBox.setOnCheckedChangeListener { _, _ ->
            updateTaskStatus()
        }

        deleteButton.setOnClickListener {
            taskListLayout.removeView(view)
            updateTaskStatus()
        }

        taskListLayout.addView(view)
    }

    private fun updateTaskStatus() {
        val taskArray = JSONArray()

        for (i in 0 until taskListLayout.childCount) {
            val taskView = taskListLayout.getChildAt(i)
            val checkBox = taskView.findViewById<CheckBox>(R.id.taskCheckbox)

            val taskObject = JSONObject()
            taskObject.put("text", checkBox.text.toString())
            taskObject.put("done", checkBox.isChecked)
            taskArray.put(taskObject)
        }

        sharedPrefs.edit().putString("task_list", taskArray.toString()).apply()
    }
}
