package com.bignerdranch.android.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ToDoList(
    @PrimaryKey
    var id: UUID = UUID.randomUUID(),
    var description: String = "",
    var priority: Int = 0,
)