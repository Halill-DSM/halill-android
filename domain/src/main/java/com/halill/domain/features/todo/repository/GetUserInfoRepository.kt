package com.halill.domain.features.todo.repository

import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface GetUserInfoRepository {
    suspend fun getUserInfo(): Flow<UserEntity>
}