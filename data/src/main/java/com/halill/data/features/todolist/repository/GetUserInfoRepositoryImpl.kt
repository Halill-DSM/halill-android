package com.halill.data.features.todolist.repository

import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.auth.entity.toEntity
import com.halill.data.local.LocalStorage
import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todolist.repository.GetUserInfoRepository
import javax.inject.Inject

class GetUserInfoRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localStorage: LocalStorage
): GetUserInfoRepository {
    override suspend fun getUserInfo(): User {
        val refreshToken = localStorage.getRefreshToken()
        remoteAuthDataSource.refreshToken(refreshToken)

        return localStorage.getUser().toEntity()
    }
}