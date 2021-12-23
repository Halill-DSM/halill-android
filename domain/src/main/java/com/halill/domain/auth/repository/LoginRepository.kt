package com.halill.domain.auth.repository

import com.halill.domain.auth.parameter.LoginParameter

interface LoginRepository {
    suspend fun login(parameter: LoginParameter)
}