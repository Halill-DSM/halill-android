package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.entity.TokenData
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource
): LoginRepository {
    override suspend fun login(parameter: LoginParameter) {
        localAuthDataSource.saveTokens(TokenData("accessToken", "refreshToken"))
    }
}