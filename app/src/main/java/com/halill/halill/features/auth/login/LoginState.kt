package com.halill.halill.features.auth.login

import com.halill.halill.base.MviState

data class LoginState(
    val email: String,
    val password: String,
    val isLoading: Boolean
) : MviState {
    companion object {
        fun initial() =
            LoginState("", "", false)
    }
}