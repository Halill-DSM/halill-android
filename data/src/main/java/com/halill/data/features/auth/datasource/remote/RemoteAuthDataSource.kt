package com.halill.data.features.auth.datasource.remote

import com.halill.data.features.auth.dto.response.LoginResponse
import com.halill.domain.auth.parameter.LoginParameter
import com.halill.domain.auth.parameter.RegisterParameter

interface RemoteAuthDataSource {
    suspend fun login(parameter: LoginParameter): LoginResponse

    suspend fun register(parameter: RegisterParameter)
}