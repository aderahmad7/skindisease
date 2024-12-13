package com.example.skindisease.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.skindisease.domain.model.DetectionResult
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(data: DetectionResult)

    @Delete
    fun deleteResult(data: DetectionResult)

    @Query("SELECT * FROM detection_result ORDER BY id DESC LIMIT :limit")
    fun getAllResult(limit: Int = 10): Flow<List<DetectionResult>>

    @Query("SELECT * FROM detection_result ORDER BY id DESC LIMIT 1")
    suspend fun getLatestResult(): DetectionResult

    @Query("SELECT * FROM detection_result WHERE id = :id")
    suspend fun getResultById(id: Int): DetectionResult
}