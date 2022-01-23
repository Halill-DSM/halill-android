package com.halill.halill.features.auth.register.model

sealed class RegisterState {
    object DoneInputState : RegisterState()
    object NotDoneInputState : RegisterState()
}