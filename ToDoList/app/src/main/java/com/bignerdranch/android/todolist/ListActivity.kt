package com.bignerdranch.android.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bignerdranch.android.todolist.database.ToDoListDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListActivity : AppCompatActivity() {
    private lateinit var todoList: RecyclerView
    private lateinit var addTaskButton: FloatingActionButton
    private lateinit var db: ToDoListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoList = findViewById(R.id.recycler_view)
        addTaskButton = findViewById(R.id.add_todo)

        db = Room.databaseBuilder(
            applicationContext,
            ToDoListDatabase::class.java, "todo-db"
        ).build()

        todoList.layoutManager = LinearLayoutManager(this)

        val tasks = mutableListOf<ToDoList>()
        val adapter = TaskAdapter(tasks, this)
        todoList.adapter = adapter

        Thread {
            tasks.addAll(db.toDoListDao().getAll())
            runOnUiThread {
                adapter.notifyDataSetChanged() // RecyclerView обновился и отобразил эти таски
            }
        }.start()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = tasks[position]

                Thread {
                    db.toDoListDao().deleteTask(task)
                    runOnUiThread {
                        tasks.removeAt(position) // Удаляем из памяти таску
                        adapter.notifyItemRemoved(position) // RecyclerView обновится, так как элемент был удален
                    }
                }.start()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(todoList)

        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
}