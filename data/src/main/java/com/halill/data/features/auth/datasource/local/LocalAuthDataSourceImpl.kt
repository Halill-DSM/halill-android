package com.halill.data.features.auth.datasource.local

import com.halill.data.features.auth.entity.TokenData
import com.halill.data.features.auth.entity.UserData
import com.halill.data.local.LocalStorage
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val localStorage: LocalStorage
): LocalAuthDataSource {
    override suspend fun saveTokens(tokenData: TokenData) {
        localStorage.saveAccessToken(tokenData.accessToken)
        localStorage.saveRefreshToken(tokenData.refreshToken)
    }

    override suspend fun saveUser(userData: UserData) {
        localStorage.saveUser(userData)
    }
}