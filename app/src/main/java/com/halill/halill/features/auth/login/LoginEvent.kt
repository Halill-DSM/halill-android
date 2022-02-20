package com.halill.halill.features.auth.login

import com.halill.halill.base.MviEvent

sealed class LoginEvent: MviEvent {
    data class SetEmail(val email: String) : LoginEvent()
    data class SetPassword(val password: String) : LoginEvent()
    object StartLoading : LoginEvent()
    object DoneLoading : LoginEvent()
}