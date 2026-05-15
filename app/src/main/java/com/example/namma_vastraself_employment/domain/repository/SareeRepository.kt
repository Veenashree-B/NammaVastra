package com.example.namma_vastraself_employment.domain.repository

import com.example.namma_vastraself_employment.domain.model.Saree
import kotlinx.coroutines.flow.Flow

interface SareeRepository {
    fun getSarees(): Flow<List<Saree>>
    suspend fun getSareeById(id: String): Saree?
    suspend fun uploadSaree(saree: Saree, images: List<android.net.Uri>): Result<Unit>
    suspend fun updateSaree(saree: Saree): Result<Unit>
    suspend fun deleteSaree(id: String): Result<Unit>
    suspend fun markAsSold(id: String): Result<Unit>
    fun getWeaverSarees(weaverId: String): Flow<List<Saree>>
}
