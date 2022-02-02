package com.halill.halill.features.auth.login.model

sealed class LoginEvent {
    object FinishLogin : LoginEvent()
    object WrongId : LoginEvent()
    object InternetError : LoginEvent()
}