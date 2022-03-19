package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.param.LoginParam

interface LoginRepository {

    suspend fun login(loginParam: LoginParam)
}