package com.halill.halill.features.auth.register

import com.halill.halill.base.MviState

data class RegisterState(
    val email: String,
    val password: String,
    val checkPassword: String,
    val name: String
) : MviState {
    companion object {
        fun initial() = RegisterState(
            email = "",
            password = "",
            checkPassword = "",
            name = ""
        )
    }
}