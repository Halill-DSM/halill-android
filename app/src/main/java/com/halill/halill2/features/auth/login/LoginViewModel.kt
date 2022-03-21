package com.halill.halill2.features.auth.login

import com.halill.domain.features.auth.param.LoginParam
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill2.base.BaseViewModel
import com.halill.halill2.base.MutableEventFlow
import com.halill.halill2.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
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
        if (!state.value.isLoading) {
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

    fun setStateInitial() {
        sendEvent(LoginEvent.InitState)
    }

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
                setState(oldState.copy(email = event.email, doneInput = true))
            }
            is LoginEvent.InputPassword -> {
                setState(oldState.copy(password = event.password, doneInput = true))
            }
            is LoginEvent.StartLoading -> {
                setState(oldState.copy(isLoading = true))
            }
            is LoginEvent.DoneLoading -> {
                setState(oldState.copy(isLoading = false))
            }
            is LoginEvent.NotDoneInput -> {
                setState(oldState.copy(doneInput = false))
            }
            is LoginEvent.InitState -> {
                setState(LoginState.initial())
            }
        }
    }
}