package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.domain.auth.parameter.LoginParameter
import com.halill.domain.auth.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
): LoginRepository {
    override suspend fun login(parameter: LoginParameter) {
        TODO("Not yet implemented")
    }
}