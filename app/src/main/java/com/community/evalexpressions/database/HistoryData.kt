package com.community.evalexpressions.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity(tableName = "history_table")
data class HistoryData(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)