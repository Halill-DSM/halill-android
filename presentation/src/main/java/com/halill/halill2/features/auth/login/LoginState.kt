package com.halill.halill2.features.auth.login

import com.halill.halill2.base.MviState

data class LoginState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val doneInput: Boolean
) : MviState {
    companion object {
        fun initial() =
            LoginState(email = "", password = "", isLoading = false,  doneInput = false)
    }
}