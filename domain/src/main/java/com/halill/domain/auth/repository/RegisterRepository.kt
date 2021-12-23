package com.halill.domain.auth.repository

import com.halill.domain.auth.parameter.RegisterParameter

interface RegisterRepository {
    suspend fun register(parameter: RegisterParameter)
}