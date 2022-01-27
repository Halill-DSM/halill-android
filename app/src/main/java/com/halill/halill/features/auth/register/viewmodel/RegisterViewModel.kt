package com.halill.halill.features.auth.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.features.auth.usecase.RegisterUseCase
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import com.halill.halill.features.auth.register.checkPassword
import com.halill.halill.features.auth.register.model.RegisterEvent
import com.halill.halill.features.auth.register.model.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.NotDoneInputState)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _registerEvent = MutableEventFlow<RegisterEvent>()
    val registerEvent: EventFlow<RegisterEvent> = _registerEvent.asEventFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _checkPassword = MutableStateFlow("")
    val checkPassword: StateFlow<String> = _checkPassword

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun setEmail(email: String) {
        viewModelScope.launch {
            _email.value = email
            checkDoneInput()
        }
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            _password.value = password
            checkDoneInput()
        }
    }

    fun setCheckPassword(password: String) {
        viewModelScope.launch {
            _checkPassword.value = password
            checkDoneInput()
        }
    }

    fun setName(name: String) {
        viewModelScope.launch {
            _name.value = name
            checkDoneInput()
        }
    }

    private fun checkDoneInput() {
        if (email.value.isNotBlank() &&
            password.value.isNotBlank() &&
            !checkPassword.value.checkPassword(password.value) &&
            name.value.isNotBlank()
        ) {
            _registerState.value = RegisterState.DoneInputState
        } else {
            _registerState.value = RegisterState.NotDoneInputState
        }
    }

    fun register() {
        viewModelScope.launch {
            val parameter = RegisterParameter(
                userEntity = UserEntity(name = name.value, email = email.value),
                password = password.value
            )
            try {
                registerUseCase.execute(parameter)
                _registerEvent.emit(RegisterEvent.FinishRegister)
            } catch (e:Exception) {
                _registerEvent.emit(RegisterEvent.FailRegister)
            }
        }
    }
}