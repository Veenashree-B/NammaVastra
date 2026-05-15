package com.example.namma_vastraself_employment.domain.model

data class Saree(
    val id: String = "",
    val weaverId: String = "",
    val weaverName: String = "",
    val weaverPhone: String = "",
    val weaverLocation: String = "",
    val imageUrls: List<String> = emptyList(),
    val title: String = "",
    val description: String = "",
    val weaveType: String = "",
    val material: String = "",
    val primaryColors: List<String> = emptyList(),
    val suggestedPrice: Double = 0.0,
    val status: String = "AVAILABLE", // AVAILABLE, SOLD
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
