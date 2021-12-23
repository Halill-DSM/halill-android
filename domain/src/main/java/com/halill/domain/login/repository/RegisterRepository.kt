package com.halill.domain.login.repository

import com.halill.domain.login.parameter.RegisterParameter

interface RegisterRepository {
    suspend fun register(parameter: RegisterParameter)
}