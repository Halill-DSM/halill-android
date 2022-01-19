package com.halill.halill.features.auth.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _checkPassword = MutableStateFlow("")
    val checkPassword: StateFlow<String> = _checkPassword

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun setPassword(password: String) {
        viewModelScope.launch {
            _password.emit(password)
        }
    }
}