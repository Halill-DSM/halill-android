package com.halill.data.local

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>
}