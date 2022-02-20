package com.halill.halill.features.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.InternetErrorException
import com.halill.domain.features.auth.exception.WrongIdException
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.Reducer
import com.halill.halill.base.asEventFlow
import com.halill.halill.features.auth.login.LoginEvent
import com.halill.halill.features.auth.login.LoginState
import com.halill.halill.features.auth.login.LoginViewEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val reducer = LoginReducer(LoginState.initial())
    val loginState: StateFlow<LoginState> = reducer.state

    private val _loginViewEffect = MutableEventFlow<LoginViewEffect>()
    val loginViewEffect = _loginViewEffect.asEventFlow()

    fun login() {
        viewModelScope.launch {
            if (doneInput()) {
                startLoading()
                try {
                    val parameter =
                        LoginParameter(loginState.value.email, loginState.value.password)
                    loginUseCase.execute(parameter)
                    _loginViewEffect.emit(LoginViewEffect.FinishLogin)
                } catch (e: WrongIdException) {
                    _loginViewEffect.emit(LoginViewEffect.WrongId)
                } catch (e: InternetErrorException) {
                    _loginViewEffect.emit(LoginViewEffect.InternetError)
                } finally {
                    doneLoading()
                }
            }
        }
    }

    private fun doneInput(): Boolean =
        loginState.value.email.isNotEmpty() && loginState.value.password.isNotEmpty()

    fun setEmail(email: String) {
        sendEvent(LoginEvent.SetEmail(email))
    }

    fun setPassword(password: String) {
        sendEvent(LoginEvent.SetPassword(password))
    }

    private fun startLoading() {
        sendEvent(LoginEvent.StartLoading)
    }

    private fun doneLoading() {
        sendEvent(LoginEvent.DoneLoading)
    }

    private class LoginReducer(initial: LoginState) : Reducer<LoginState, LoginEvent>(initial) {
        override fun reduce(oldState: LoginState, event: LoginEvent) {
            when (event) {
                is LoginEvent.SetEmail -> {
                    setState(oldState.copy(email = event.email))
                }
                is LoginEvent.SetPassword -> {
                    setState(oldState.copy(password = event.password))
                }
                is LoginEvent.StartLoading -> {
                    setState(oldState.copy(isLoading = true))
                }
                is LoginEvent.DoneLoading -> {
                    setState(oldState.copy(isLoading = false))
                }
            }
        }

    }

    private fun sendEvent(event: LoginEvent) {
        reducer.sendEvent(event)
    }
}