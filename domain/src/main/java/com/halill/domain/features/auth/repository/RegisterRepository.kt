package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.parameter.RegisterParameter

interface RegisterRepository {
    suspend fun register(parameter: RegisterParameter)
}