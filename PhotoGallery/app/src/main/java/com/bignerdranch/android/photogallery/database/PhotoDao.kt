package com.bignerdranch.android.photogallery.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.photogallery.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAll(): List<Photo>

    @Insert
    fun addPhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}