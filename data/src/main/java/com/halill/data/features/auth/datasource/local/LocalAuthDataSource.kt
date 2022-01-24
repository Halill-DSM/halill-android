package com.halill.data.features.auth.datasource.local

import com.halill.data.features.auth.entity.TokenData
import com.halill.data.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow

interface LocalAuthDataSource {
    suspend fun saveTokens(tokenData: TokenData)

    suspend fun saveUser(userData: UserData)

    suspend fun getRefreshToken(): Flow<String?>

    suspend fun getUser(): Flow<UserData>
}