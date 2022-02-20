package com.halill.halill.features.auth.login

import com.halill.halill.base.MviEvent

sealed class LoginEvent: MviEvent {
    data class InputEmail(val email: String) : LoginEvent()
    data class InputPassword(val password: String) : LoginEvent()
    object StartLoading : LoginEvent()
    object DoneLoading : LoginEvent()
}