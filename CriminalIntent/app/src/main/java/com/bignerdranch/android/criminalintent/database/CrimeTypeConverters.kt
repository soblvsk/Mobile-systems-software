package com.bignerdranch.android.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

    @TypeConverter
    fun toUUID(uuid: String?) : UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: String?) : String? {
        return uuid?.toString()
    }
}