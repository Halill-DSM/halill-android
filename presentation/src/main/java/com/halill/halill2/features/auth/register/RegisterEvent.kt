package com.halill.halill2.features.auth.register

import com.halill.halill2.base.MviEvent

sealed class RegisterEvent : MviEvent {
    data class InputEmail(val email: String) : RegisterEvent()
    data class InputPassword(val password: String) : RegisterEvent()
    data class InputCheckPassword(val checkPassword: String) : RegisterEvent()
    object StartLoading : RegisterEvent()
    object FinishLoading : RegisterEvent()
}