package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.param.RegisterParam

interface RegisterRepository {

    suspend fun register(registerParam: RegisterParam)
}