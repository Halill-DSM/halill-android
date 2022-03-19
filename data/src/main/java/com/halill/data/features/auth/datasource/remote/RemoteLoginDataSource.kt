package com.halill.data.features.auth.datasource.remote

import com.halill.domain.features.auth.param.LoginParam

interface RemoteLoginDataSource {

    suspend fun login(loginParam: LoginParam)
}