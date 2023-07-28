package com.community.evalexpressions.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}