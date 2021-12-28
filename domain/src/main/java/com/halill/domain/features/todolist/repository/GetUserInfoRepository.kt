package com.halill.domain.features.todolist.repository

import com.halill.domain.features.auth.entity.User

interface GetUserInfoRepository {
    suspend fun getUserInfo(): User
}