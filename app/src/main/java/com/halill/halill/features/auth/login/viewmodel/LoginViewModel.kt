package com.halill.halill.features.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.InternetErrorException
import com.halill.domain.features.auth.exception.WrongIdException
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.features.auth.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<LoginState>(LoginState.NotDoneInputState)
    val loginState: StateFlow<LoginState> = _loginState

    fun login() {
        viewModelScope.launch {
            if (email.single().isNotEmpty() && password.single().isNotEmpty())

                try {
                    loginUseCase.execute(LoginParameter(email.single(), password.single()))
                    _loginState.value = LoginState.FinishState
                } catch (e: WrongIdException) {
                    _loginState.value = LoginState.WrongIdState
                } catch (e: InternetErrorException) {
                    _loginState.value = LoginState.InternetExceptionState
                }
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        viewModelScope.launch {
            _password.emit(password)
        }
    }

    fun setDoneLoginState(email: String, password: String) {
        viewModelScope.launch {
            _loginState.emit(LoginState.DoneInputState(email, password))
        }
    }

    fun setNotDoneInputState() {
        viewModelScope.launch {
            _loginState.emit(LoginState.NotDoneInputState)
        }
    }
}