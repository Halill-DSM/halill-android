package com.halill.halill2.features.auth.register

import com.halill.halill2.base.MviState

data class RegisterState(
    val email: String,
    val password: String,
    val checkPassword: String,
    val isLoading: Boolean
) : MviState {
    companion object {
        fun initial() = RegisterState(
            email = "",
            password = "",
            checkPassword = "",
            isLoading = false
        )
    }
}