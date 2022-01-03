package com.halill.halill.features.auth.login.model

sealed class LoginState {
    object TypingState : LoginState()
    object NotDoneInputState : LoginState()
    data class DoneInputState(val id: String, val password: String) : LoginState()
    object LoadingState : LoginState()
    object FinishState : LoginState()
    object WrongIdState : LoginState()
    object InternetExceptionState : LoginState()
}