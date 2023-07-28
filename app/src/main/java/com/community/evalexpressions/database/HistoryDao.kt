package com.community.evalexpressions.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyItem: HistoryData)
}