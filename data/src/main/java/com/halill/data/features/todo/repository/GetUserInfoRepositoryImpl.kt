package com.halill.data.features.todo.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.entity.toEntity
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoRepositoryImpl @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource
) : GetUserInfoRepository {
    override suspend fun getUserInfo(): Flow<UserEntity> =
        localAuthDataSource.getUser().toEntity()

}