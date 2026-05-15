package com.example.namma_vastraself_employment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.namma_vastraself_employment.domain.model.TrendItem

@Entity(tableName = "trends")
data class TrendEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val title: String,
    val titleKannada: String,
    val category: String,
    val colorPalette: String, // Comma separated values
    val monthYear: String,
    val isActive: Boolean,
    val createdAt: Long
)

fun TrendEntity.toTrendItem() = TrendItem(
    id = id,
    imageUrl = imageUrl,
    title = title,
    titleKannada = titleKannada,
    category = category,
    colorPalette = colorPalette.split(",").filter { it.isNotBlank() },
    monthYear = monthYear,
    isActive = isActive,
    createdAt = createdAt
)

fun TrendItem.toTrendEntity() = TrendEntity(
    id = id,
    imageUrl = imageUrl,
    title = title,
    titleKannada = titleKannada,
    category = category,
    colorPalette = colorPalette.joinToString(","),
    monthYear = monthYear,
    isActive = isActive,
    createdAt = createdAt
)
