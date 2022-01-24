package com.halill.halill.features.auth.register.model

sealed class RegisterEvent {
    object FinishRegister : RegisterEvent()
    object FailRegister : RegisterEvent()
}