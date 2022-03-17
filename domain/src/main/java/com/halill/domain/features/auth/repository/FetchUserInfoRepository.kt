package com.halill.domain.features.auth.repository

import com.halill.domain.features.auth.entity.UserEntity

interface FetchUserInfoRepository {

    suspend fun fetchUserInfo(): UserEntity
}