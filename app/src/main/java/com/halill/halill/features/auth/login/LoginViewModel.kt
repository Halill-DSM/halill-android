package com.halill.halill.features.auth.login

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.param.LoginParam
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginState, LoginEvent>() {

    override val initialState: LoginState
        get() = LoginState.initial()

    private val _loginViewEffect = MutableEventFlow<LoginViewEffect>()
    val loginViewEffect = _loginViewEffect.asEventFlow()

    suspend fun login() {
        if (doneInput()) {
            startLoading()
            val parameter =
                LoginParam(email = state.value.email, password = state.value.password)

            kotlin.runCatching {
                loginUseCase.execute(parameter)
            }.onSuccess {
                _loginViewEffect.emit(LoginViewEffect.FinishLogin)
            }.onFailure {
                _loginViewEffect.emit(LoginViewEffect.WrongId)
            }.also {
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

    suspend fun notDoneInput() {
        _loginViewEffect.emit(LoginViewEffect.NotDoneInput)
        sendEvent(LoginEvent.NotDoneInput)
    }

    override fun reduceEvent(oldState: LoginState, event: LoginEvent) {
        when (event) {
            is LoginEvent.InputEmail -> {
                setState(oldState.copy(email = event.email, notDoneInput = false))
            }
            is LoginEvent.InputPassword -> {
                setState(oldState.copy(password = event.password, notDoneInput = false))
            }
            is LoginEvent.StartLoading -> {
                setState(oldState.copy(isLoading = true))
            }
            is LoginEvent.DoneLoading -> {
                setState(oldState.copy(isLoading = false))
            }
            is LoginEvent.NotDoneInput -> {
                setState(oldState.copy(notDoneInput = true))
            }
        }
    }
}