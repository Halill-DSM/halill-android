package com.halill.halill.features.auth.register

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.param.RegisterParam
import com.halill.domain.features.auth.usecase.RegisterUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState, RegisterEvent>() {

    override val initialState: RegisterState
        get() = RegisterState.initial()

    private val _registerViewEffect = MutableEventFlow<RegisterViewEffect>()
    val registerViewEffect: EventFlow<RegisterViewEffect> = _registerViewEffect.asEventFlow()

    fun setEmail(email: String) {
        sendEvent(RegisterEvent.InputEmail(email))
    }

    fun setPassword(password: String) {
        sendEvent(RegisterEvent.InputPassword(password))
    }

    fun setCheckPassword(password: String) {
        sendEvent(RegisterEvent.InputCheckPassword(password))
    }

    private fun startLoading() {
        sendEvent(RegisterEvent.StartLoading)
    }

    private fun finishLoading() {
        sendEvent(RegisterEvent.FinishLoading)
    }

    fun register() {
        val state = state.value
        if (!state.isLoading) {
            startLoading()
            val parameter = RegisterParam(
                email = state.email,
                password = state.password
            )
            viewModelScope.launch {
                kotlin.runCatching {
                    registerUseCase.execute(parameter)
                }.onSuccess {
                    _registerViewEffect.emit(RegisterViewEffect.FinishRegister)
                }.onFailure {
                    _registerViewEffect.emit(RegisterViewEffect.FailRegister)
                }.also {
                    finishLoading()
                }
            }
        }

    }

    override fun reduceEvent(oldState: RegisterState, event: RegisterEvent) {
        when (event) {
            is RegisterEvent.InputEmail -> {
                setState(
                    oldState.copy(email = event.email)
                )
            }
            is RegisterEvent.InputPassword -> {
                setState(
                    oldState.copy(password = event.password)
                )
            }
            is RegisterEvent.InputCheckPassword -> {
                setState(
                    oldState.copy(checkPassword = event.checkPassword)
                )
            }
            is RegisterEvent.FinishLoading -> {
                setState(
                    oldState.copy(isLoading = false)
                )
            }
            is RegisterEvent.StartLoading -> {
                setState(
                    oldState.copy(isLoading = true)
                )
            }
        }
    }
}