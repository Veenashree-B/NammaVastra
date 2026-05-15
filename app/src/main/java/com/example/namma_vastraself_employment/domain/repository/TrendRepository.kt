package com.example.namma_vastraself_employment.domain.repository

import com.example.namma_vastraself_employment.domain.model.TrendItem
import kotlinx.coroutines.flow.Flow

interface TrendRepository {
    fun getTrends(): Flow<List<TrendItem>>
    suspend fun saveTrend(trend: TrendItem)
    suspend fun deleteTrend(id: String)
    fun getSavedTrends(): Flow<List<TrendItem>>
}
