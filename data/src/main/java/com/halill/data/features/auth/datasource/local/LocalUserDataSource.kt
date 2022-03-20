package com.halill.data.features.auth.datasource.local

import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {

    suspend fun fetchUser(): Flow<UserEntity>

    suspend fun deleteUser()

    suspend fun fetchIsLoginState(): Flow<Boolean>

    fun saveUserName(name: String)
}