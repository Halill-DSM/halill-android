package com.halill.halill.features.auth.login

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.Reducer
import com.halill.halill.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState>() {

    private val reducer = LoginReducer(LoginState.initial())
    override val state = reducer.state

    private val _loginViewEffect = MutableEventFlow<LoginViewEffect>()
    val loginViewEffect = _loginViewEffect.asEventFlow()

    fun login() {
        viewModelScope.launch {
            if (doneInput()) {
                startLoading()
                val parameter =
                    LoginParameter(email = state.value.email, password = state.value.password)
                loginUseCase.execute(parameter)
                _loginViewEffect.emit(LoginViewEffect.FinishLogin)
                doneLoading()
            }
        }
    }

    private fun doneInput(): Boolean =
        state.value.email.isNotEmpty() && state.value.password.isNotEmpty()

    fun setEmail(email: String) {
        sendEvent(LoginEvent.InputEmail(email))
    }

    fun setPassword(password: String) {
        sendEvent(LoginEvent.InputPassword(password))
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
                is LoginEvent.InputEmail -> {
                    setState(oldState.copy(email = event.email))
                }
                is LoginEvent.InputPassword -> {
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