package com.halill.data.local.datastorage

import kotlinx.coroutines.flow.Flow

interface LocalStorage {

    suspend fun isLoginState(): Flow<Boolean>

    suspend fun saveIsLoginState()
}