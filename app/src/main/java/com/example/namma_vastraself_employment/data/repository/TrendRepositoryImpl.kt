package com.example.namma_vastraself_employment.data.repository

import com.example.namma_vastraself_employment.data.local.dao.TrendDao
import com.example.namma_vastraself_employment.data.local.entity.toTrendEntity
import com.example.namma_vastraself_employment.data.local.entity.toTrendItem
import com.example.namma_vastraself_employment.domain.model.TrendItem
import com.example.namma_vastraself_employment.domain.repository.TrendRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrendRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val trendDao: TrendDao
) : TrendRepository {

    private val dummyTrends = listOf(
        TrendItem(
            id = "t1",
            imageUrl = "https://images.unsplash.com/photo-1610189012906-4009a96e987c?w=800",
            title = "Emerald Green Silk Ilkal",
            category = "Traditional Wear",
            colorPalette = listOf("#004d40", "#ffd700"),
            monthYear = "May 2024"
        ),
        TrendItem(
            id = "t2",
            imageUrl = "https://images.unsplash.com/photo-1617627143750-d86bc21e42bb?w=800",
            title = "Magenta Banarasi Brocade",
            category = "Festive Special",
            colorPalette = listOf("#c2185b", "#ffd700"),
            monthYear = "May 2024"
        ),
        TrendItem(
            id = "t3",
            imageUrl = "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800",
            title = "Classic Mustard Gold Zari",
            category = "Wedding Collection",
            colorPalette = listOf("#ffb300", "#1b5e20"),
            monthYear = "June 2024"
        ),
        TrendItem(
            id = "t4",
            imageUrl = "https://images.unsplash.com/photo-1583391733990-28e4693b6e76?w=800",
            title = "Peacock Blue Molakalmuru",
            category = "Handloom Luxury",
            colorPalette = listOf("#01579b", "#006064"),
            monthYear = "June 2024"
        ),
        TrendItem(
            id = "t5",
            imageUrl = "https://images.unsplash.com/photo-1611601679655-7ca3ce40498b?w=800",
            title = "Crimson Red Temple Border",
            category = "Daily Wear",
            colorPalette = listOf("#b71c1c", "#fdd835"),
            monthYear = "July 2024"
        ),
        TrendItem(
            id = "t6",
            imageUrl = "https://images.unsplash.com/photo-1590736961141-863776632789?w=800",
            title = "Pastel Cotton Jamdani",
            category = "Summer Trend",
            colorPalette = listOf("#f8bbd0", "#e1f5fe"),
            monthYear = "July 2024"
        )
    )

    override fun getTrends(): Flow<List<TrendItem>> = callbackFlow {
        val subscription = firestore.collection("trends")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || snapshot.isEmpty) {
                    trySend(dummyTrends)
                } else {
                    val trends = snapshot.toObjects(TrendItem::class.java)
                    if (trends.isEmpty()) trySend(dummyTrends) else trySend(trends)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun saveTrend(trend: TrendItem) {
        trendDao.insertTrend(trend.toTrendEntity())
    }

    override suspend fun deleteTrend(id: String) {
        val entity = trendDao.getTrendById(id)
        if (entity != null) {
            trendDao.deleteTrend(entity)
        }
    }

    override fun getSavedTrends(): Flow<List<TrendItem>> {
        return trendDao.getAllTrends().map { entities ->
            entities.map { it.toTrendItem() }
        }
    }
}
