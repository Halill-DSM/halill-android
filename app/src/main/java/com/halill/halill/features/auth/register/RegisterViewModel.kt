package com.halill.halill.features.auth.register

import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.RegisterFailedException
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

    fun setName(name: String) {
        sendEvent(RegisterEvent.InputName(name))
    }

    fun register() {
        val state = state.value
        val parameter = RegisterParam(
            name = state.name,
            email = state.email,
            password = state.password
        )
        viewModelScope.launch {
            try {
                registerUseCase.execute(parameter)
                _registerViewEffect.emit(RegisterViewEffect.FinishRegister)
            } catch (e: RegisterFailedException) {

            }
        }
    }

    override fun reduceEvent(oldState: RegisterState, event: RegisterEvent) {
        when (event) {
            is RegisterEvent.InputEmail -> {
                setState(oldState.copy(email = event.email))
            }
            is RegisterEvent.InputPassword -> {
                setState(oldState.copy(password = event.password))
            }
            is RegisterEvent.InputCheckPassword -> {
                setState(oldState.copy(checkPassword = event.checkPassword))
            }
            is RegisterEvent.InputName -> {
                setState(oldState.copy(name = event.name))
            }
        }
    }
}