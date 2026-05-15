package com.example.namma_vastraself_employment.domain.model

data class TrendItem(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val titleKannada: String = "",
    val category: String = "",
    val colorPalette: List<String> = emptyList(),
    val monthYear: String = "",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
