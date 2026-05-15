package com.example.namma_vastraself_employment.presentation.screens.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namma_vastraself_employment.domain.model.Saree
import com.example.namma_vastraself_employment.domain.repository.AuthRepository
import com.example.namma_vastraself_employment.domain.repository.SareeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val sareeRepository: SareeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uploadSuccess = MutableStateFlow(false)
    val uploadSuccess = _uploadSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun uploadSaree(
        title: String,
        description: String,
        weaveType: String,
        material: String,
        price: Double,
        images: List<Uri>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null) {
                _error.value = "User not logged in"
                _isLoading.value = false
                return@launch
            }

            val saree = Saree(
                id = UUID.randomUUID().toString(),
                weaverId = currentUser.id,
                weaverName = currentUser.name,
                weaverPhone = currentUser.phone,
                weaverLocation = currentUser.location,
                title = title,
                description = description,
                weaveType = weaveType,
                material = material,
                suggestedPrice = price,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            val result = sareeRepository.uploadSaree(saree, images)
            if (result.isSuccess) {
                _uploadSuccess.value = true
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Upload failed"
            }
            _isLoading.value = false
        }
    }
    
    fun resetState() {
        _uploadSuccess.value = false
        _error.value = null
    }
}
