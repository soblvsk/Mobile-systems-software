package com.bignerdranch.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.room.Room
import com.bignerdranch.android.todolist.database.ToDoListDatabase

class AddActivity : AppCompatActivity() {

    private lateinit var taskDescription: EditText
    private lateinit var priorityGroup: RadioGroup
    private lateinit var addButton: Button
    private lateinit var db: ToDoListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setTitle("Add a New Task")

        taskDescription = findViewById(R.id.taskDescription)
        priorityGroup = findViewById(R.id.priorityGroup)
        addButton = findViewById(R.id.button_add)

        db = Room.databaseBuilder(
            applicationContext,
            ToDoListDatabase::class.java, "todo-db"
        ).build()

        addButton.setOnClickListener {
            val description = taskDescription.text.toString()

            val priority = when (priorityGroup.checkedRadioButtonId) {
                R.id.radio_high -> 1
                R.id.radio_medium -> 2
                R.id.radio_low -> 3
                else -> 1
            }

            val task = ToDoList(description = description, priority = priority)
            Thread {
                db.toDoListDao().addTask(task)
            }.start()

            finish()
        }
    }
}