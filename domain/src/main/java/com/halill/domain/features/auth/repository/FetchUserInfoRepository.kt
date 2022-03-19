package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface FetchUserInfoRepository {

    suspend fun fetchUserInfo(): Flow<UserEntity>
}