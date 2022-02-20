package com.halill.halill.features.auth.register

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.features.auth.usecase.RegisterUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterState>() {

    private val reducer = RegisterReducer(RegisterState.initial())
    override val state = reducer.state

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
        viewModelScope.launch {
            val parameter = RegisterParameter(
                userEntity = UserEntity(name = state.value.name, email = state.value.email),
                password = state.value.password
            )
            try {
                registerUseCase.execute(parameter)
                _registerViewEffect.emit(RegisterViewEffect.FinishRegister)
            } catch (e:Exception) {
                _registerViewEffect.emit(RegisterViewEffect.FailRegister)
            }
        }
    }

    private class RegisterReducer(initial: RegisterState): Reducer<RegisterState, RegisterEvent>(initial) {
        override fun reduce(oldState: RegisterState, event: RegisterEvent) {
            when(event) {
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

    private fun sendEvent(event: RegisterEvent) {
        reducer.sendEvent(event)
    }
}