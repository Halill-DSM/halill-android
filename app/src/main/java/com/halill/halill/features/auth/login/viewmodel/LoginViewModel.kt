package com.halill.halill.features.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.InternetErrorException
import com.halill.domain.features.auth.exception.WrongIdException
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import com.halill.halill.features.auth.login.model.LoginEvent
import com.halill.halill.features.auth.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _loginEvent = MutableEventFlow<LoginEvent>()
    val loginEvent = _loginEvent.asEventFlow()

    fun login() {
        viewModelScope.launch {
            _loginState.emit(LoginState.LoadingState)
            if (email.value.isNotEmpty() && password.value.isNotEmpty())
                try {
                    val parameter = LoginParameter(email.value, password.value)
                    loginUseCase.execute(parameter)
                    _loginEvent.emit(LoginEvent.FinishLogin)
                } catch (e: WrongIdException) {
                    _loginEvent.emit(LoginEvent.WrongId)
                } catch (e: InternetErrorException) {
                    _loginEvent.emit(LoginEvent.InternetError)
                } finally {
                    _loginState.emit(LoginState.NotDoneInputState)
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

    private fun checkDoneInput() {
        _loginState.value =
            if (email.value.isEmpty() || password.value.isEmpty()) LoginState.NotDoneInputState else LoginState.DoneInputState(
                email.value,
                password.value
            )
    }
}