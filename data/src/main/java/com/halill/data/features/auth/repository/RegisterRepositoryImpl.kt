package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.domain.features.auth.param.RegisterParam
import com.halill.domain.features.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val remoteRegisterDataSource: RemoteRegisterDataSource
): RegisterRepository {

    override suspend fun register(registerParam: RegisterParam) {
        remoteRegisterDataSource.register(registerParam)
    }
}