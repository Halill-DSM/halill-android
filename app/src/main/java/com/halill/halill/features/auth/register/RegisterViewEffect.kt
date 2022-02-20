package com.halill.halill.features.auth.register

sealed class RegisterViewEffect {
    object FinishRegister : RegisterViewEffect()
    object FailRegister : RegisterViewEffect()
}