package com.halill.data.features.todo.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.auth.entity.toEntity
import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import javax.inject.Inject

class GetUserInfoRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
): GetUserInfoRepository {
    override suspend fun getUserInfo(): User {
        val refreshToken = localAuthDataSource.getRefreshToken()
        remoteAuthDataSource.refreshToken(refreshToken)

        return localAuthDataSource.getUser().toEntity()
    }
}