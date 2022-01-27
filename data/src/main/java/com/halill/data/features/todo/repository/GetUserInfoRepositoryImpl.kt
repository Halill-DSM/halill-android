package com.halill.data.features.todo.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.auth.entity.toEntity
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUserInfoRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
) : GetUserInfoRepository {
    override suspend fun getUserInfo(): Flow<UserEntity> {
        val refreshTokenJob = Job()
        CoroutineScope(Dispatchers.Default + refreshTokenJob).launch {
            localAuthDataSource.getRefreshToken().collect {
                remoteAuthDataSource.refreshToken(it)
            }
        }
        refreshTokenJob.cancel()

        return localAuthDataSource.getUser().toEntity()
    }
}