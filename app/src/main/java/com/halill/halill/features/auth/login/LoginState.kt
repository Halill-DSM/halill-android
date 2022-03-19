package com.halill.halill.features.auth.login

import com.halill.halill.base.MviState

data class LoginState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val notDoneInput: Boolean
) : MviState {
    companion object {
        fun initial() =
            LoginState(email = "", password = "", isLoading = false,  notDoneInput = false)
    }
}