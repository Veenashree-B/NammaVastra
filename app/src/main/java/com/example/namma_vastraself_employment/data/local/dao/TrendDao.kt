package com.example.namma_vastraself_employment.data.local.dao

import androidx.room.*
import com.example.namma_vastraself_employment.data.local.entity.TrendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendDao {
    @Query("SELECT * FROM trends ORDER BY createdAt DESC")
    fun getAllTrends(): Flow<List<TrendEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrend(trend: TrendEntity)

    @Delete
    suspend fun deleteTrend(trend: TrendEntity)

    @Query("SELECT * FROM trends WHERE id = :id")
    suspend fun getTrendById(id: String): TrendEntity?
}
