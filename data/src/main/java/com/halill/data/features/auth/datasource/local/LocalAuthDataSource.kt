package com.halill.data.features.auth.datasource.local

import com.halill.data.features.auth.entity.TokenData

interface LocalAuthDataSource {
    suspend fun saveTokens(tokenData: TokenData)
}