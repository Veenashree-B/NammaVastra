package com.example.namma_vastraself_employment.domain.repository

import com.example.namma_vastraself_employment.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun loginWithPhone(phone: String): Result<Unit>
    suspend fun verifyOtp(otp: String): Result<User>
    suspend fun logout()
    suspend fun updateProfile(user: User): Result<Unit>
    suspend fun getCurrentUser(): User?
}
