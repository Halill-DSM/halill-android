package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.parameter.LoginParameter

interface LoginRepository {
    suspend fun login(parameter: LoginParameter)
}