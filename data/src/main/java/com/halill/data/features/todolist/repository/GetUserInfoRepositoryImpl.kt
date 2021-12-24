package com.halill.data.features.todolist.repository

import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todolist.repository.GetUserInfoRepository

class GetUserInfoRepositoryImpl: GetUserInfoRepository {
    override suspend fun getUserInfo(): User {
        TODO("Not yet implemented")
    }
}