package com.bignerdranch.android.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.todolist.ToDoList

@Dao
interface ToDoListDao {

    @Query("SELECT * FROM ToDoList ORDER BY priority DESC")
    fun getAll(): LiveData<List<ToDoList>>

    @Insert
    fun addTask(task: ToDoList)

    @Delete
    fun deleteTask(task: ToDoList)

}
