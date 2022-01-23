package com.halill.domain.features.todo.repository

import com.halill.domain.features.auth.entity.User
import kotlinx.coroutines.flow.Flow

interface GetUserInfoRepository {
    suspend fun getUserInfo(): Flow<User>
}