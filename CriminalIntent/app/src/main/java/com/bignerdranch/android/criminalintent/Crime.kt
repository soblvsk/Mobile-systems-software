package com.bignerdranch.android.criminalintent


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Crime(@PrimaryKey val id: UUID,
                 var title: String,
                 var date: Date,
                 var isSolved: Boolean)