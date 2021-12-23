package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.domain.auth.parameter.RegisterParameter
import com.halill.domain.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
): RegisterRepository {
    override suspend fun register(parameter: RegisterParameter) {
        TODO("Not yet implemented")
    }
}