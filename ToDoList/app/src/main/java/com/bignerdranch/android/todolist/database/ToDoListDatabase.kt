package com.bignerdranch.android.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.todolist.ToDoList

@Database(entities = [ToDoList::class], version = 1)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
}