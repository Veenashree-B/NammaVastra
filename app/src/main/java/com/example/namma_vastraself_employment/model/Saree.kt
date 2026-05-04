package com.example.namma_vastraself_employment.model

data class Saree(
    val id: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val phoneNumber: String = ""
)

data class TrendItem(
    val imageUrl: String,
    val title: String
)