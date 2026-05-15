package com.example.namma_vastraself_employment.presentation.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namma_vastraself_employment.domain.model.Saree
import com.example.namma_vastraself_employment.domain.repository.SareeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: SareeRepository
) : ViewModel() {

    private val _sarees = MutableStateFlow<List<Saree>>(emptyList())
    val sarees: StateFlow<List<Saree>> = _sarees.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadSarees()
    }

    fun loadSarees() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getSarees()
                .onEach { _sarees.value = it }
                .onCompletion { _isLoading.value = false }
                .catch { /* Handle error */ }
                .collect()
        }
    }
}
