package com.halill.halill2.features.auth.register

sealed class RegisterViewEffect {
    object FinishRegister : RegisterViewEffect()
    object FailRegister : RegisterViewEffect()
}