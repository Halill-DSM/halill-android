package com.halill.data.local.localstorage

import com.halill.data.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(token: String)

    suspend fun saveUser(userData: UserData)

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun getUser(): UserData
}