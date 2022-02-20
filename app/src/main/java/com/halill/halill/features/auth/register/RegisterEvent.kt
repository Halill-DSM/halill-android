package com.halill.halill.features.auth.register

sealed class RegisterEvent {
    object FinishRegister : RegisterEvent()
    object FailRegister : RegisterEvent()
}