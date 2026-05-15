package com.example.namma_vastraself_employment.presentation.screens.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namma_vastraself_employment.domain.model.TrendItem
import com.example.namma_vastraself_employment.domain.repository.TrendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    private val repository: TrendRepository
) : ViewModel() {

    private val _trends = MutableStateFlow<List<TrendItem>>(emptyList())
    val trends: StateFlow<List<TrendItem>> = _trends.asStateFlow()

    private val _savedTrends = MutableStateFlow<List<TrendItem>>(emptyList())
    val savedTrends: StateFlow<List<TrendItem>> = _savedTrends.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadTrends()
        loadSavedTrends()
    }

    fun loadTrends() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTrends()
                .onEach { _trends.value = it }
                .onCompletion { _isLoading.value = false }
                .catch { /* Handle error */ }
                .collect()
        }
    }

    private fun loadSavedTrends() {
        viewModelScope.launch {
            repository.getSavedTrends().collect {
                _savedTrends.value = it
            }
        }
    }

    fun saveTrend(trend: TrendItem) {
        viewModelScope.launch {
            repository.saveTrend(trend)
        }
    }

    fun deleteTrend(id: String) {
        viewModelScope.launch {
            repository.deleteTrend(id)
        }
    }
}
