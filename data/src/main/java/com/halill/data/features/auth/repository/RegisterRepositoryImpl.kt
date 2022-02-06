package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.features.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource
): RegisterRepository {
    override suspend fun register(parameter: RegisterParameter) {
        remoteAuthDataSource.register(parameter)
    }
}