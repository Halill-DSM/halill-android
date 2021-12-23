package com.halill.data.features.auth.dto.request

import com.halill.domain.auth.parameter.LoginParameter

data class LoginRequest(val email: String, val password: String)

fun LoginParameter.toRequest() =
    LoginRequest(
        email = this.user.email,
        password = this.password
    )