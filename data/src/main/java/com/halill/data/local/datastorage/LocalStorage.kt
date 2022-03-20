package com.halill.data.local.datastorage

import kotlinx.coroutines.flow.Flow

interface LocalStorage {

    suspend fun isLoginState(): Flow<Boolean>

    suspend fun saveIsLoginState()

    suspend fun saveIsNotLoginState()

    suspend fun fetchAllTimeCount(): Flow<Int>

    suspend fun plusOneAllTimeCount()

    suspend fun fetchAllTimeDoneCount(): Flow<Int>

    suspend fun plusOneAllTimeDoneCount()
}