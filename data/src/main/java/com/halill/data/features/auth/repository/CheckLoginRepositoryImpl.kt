package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.repository.CheckLoginRepository
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CheckLoginRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource
): CheckLoginRepository {
    override suspend fun checkLogin() {
        localAuthDataSource.getRefreshToken().collect { refreshToken ->
            if (refreshToken.isNullOrEmpty()) {
                throw NotLoginException()
            }
            remoteAuthDataSource.refreshToken(refreshToken)
        }
    }
}