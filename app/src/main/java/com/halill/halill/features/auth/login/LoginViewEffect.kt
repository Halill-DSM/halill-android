package com.halill.halill.features.auth.login

sealed class LoginViewEffect {
    object FinishLogin : LoginViewEffect()
    object WrongId : LoginViewEffect()
    object NotDoneInput : LoginViewEffect()
    object InternetError : LoginViewEffect()
}