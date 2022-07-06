package com.halill.halill2.features.auth.login

sealed class LoginViewEffect {
    object FinishLogin : LoginViewEffect()
    object WrongId : LoginViewEffect()
    object NotDoneInput : LoginViewEffect()
    object InternetError : LoginViewEffect()
}