package com.halill.data.features.auth.dto.request

import com.halill.domain.features.auth.parameter.RegisterParameter

data class RegisterRequest(val email: String, val password: String, val name: String)

fun RegisterParameter.toRequest() =
    RegisterRequest(
        email = user.email,
        password = password,
        name = user.name
    )