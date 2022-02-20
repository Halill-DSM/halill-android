package com.halill.halill.features.auth.register

sealed class RegisterState {
    object DoneInputState : RegisterState()
    object NotDoneInputState : RegisterState()
}