package com.bignerdranch.android.photogallery.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.photogallery.Photo

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}