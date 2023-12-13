package com.bignerdranch.android.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: List<ToDoList>, private val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // вызывается, когда RecyclerView нуждается в новом ViewHolder для отображения элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    // Вызывается, чтобы обновить ViewHolder для отображения новых данных для определенной позиции
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val textView = holder.view.findViewById<TextView>(R.id.task_text)
        val priorityView = holder.view.findViewById<TextView>(R.id.task_priority)

        textView.text = task.description

        val color = when (task.priority) {
            1 -> ContextCompat.getColor(context, R.color.high_priority)
            2 -> ContextCompat.getColor(context, R.color.medium_priority)
            3 -> ContextCompat.getColor(context, R.color.low_priority)
            else -> ContextCompat.getColor(context, R.color.high_priority)
        }

        val drawable = priorityView.background
        DrawableCompat.setTint(drawable, color)

        priorityView.text = task.priority.toString()
    }

    // возвращает количество элементов, которые будут отображаться в RecyclerView
    override fun getItemCount() = tasks.size
}


