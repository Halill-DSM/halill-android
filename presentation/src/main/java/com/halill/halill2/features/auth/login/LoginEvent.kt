package com.halill.halill2.features.auth.login

import com.halill.halill2.base.MviEvent

sealed class LoginEvent: MviEvent {
    data class InputEmail(val email: String) : LoginEvent()
    data class InputPassword(val password: String) : LoginEvent()
    object NotDoneInput : LoginEvent()
    object StartLoading : LoginEvent()
    object DoneLoading : LoginEvent()
    object InitState : LoginEvent()
}