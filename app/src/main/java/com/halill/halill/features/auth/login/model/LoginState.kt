package com.halill.halill.features.auth.login.model

sealed class LoginState {
    object NotDoneInputState : LoginState()
    data class DoneInputState(val email: String, val password: String) : LoginState()
    object LoadingState : LoginState()
    object InternetExceptionState : LoginState()
}