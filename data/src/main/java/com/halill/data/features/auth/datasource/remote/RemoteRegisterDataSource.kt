package com.halill.data.features.auth.datasource.remote

import com.halill.domain.features.auth.param.RegisterParam

interface RemoteRegisterDataSource {

    suspend fun register(registerParam: RegisterParam)
}