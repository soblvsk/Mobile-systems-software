package com.bignerdranch.android.photogallery

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Photos")
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val url: String,
    var title: String
)