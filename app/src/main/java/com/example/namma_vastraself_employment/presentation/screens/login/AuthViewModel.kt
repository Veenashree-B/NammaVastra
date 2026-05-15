package com.example.namma_vastraself_employment.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namma_vastraself_employment.domain.model.User
import com.example.namma_vastraself_employment.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            repository.currentUser.collect {
                _user.value = it
            }
        }
    }

    fun loginWithPhone(phone: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.loginWithPhone(phone)
            _isLoading.value = false
            if (result.isFailure) {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.verifyOtp(otp)
            _isLoading.value = false
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun clearError() {
        _error.value = null
    }
}
