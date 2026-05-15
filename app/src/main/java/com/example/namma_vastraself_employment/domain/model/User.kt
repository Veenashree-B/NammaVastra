package com.example.namma_vastraself_employment.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val nameKannada: String = "",
    val phone: String = "",
    val district: String = "",
    val location: String = "",
    val specialty: String = "",
    val experience: String = "",
    val bio: String = "",
    val profileImageUrl: String = ""
)
