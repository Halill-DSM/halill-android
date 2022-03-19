package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import javax.inject.Inject

class FetchUserInfoRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : FetchUserInfoRepository {

    override suspend fun fetchUserInfo(): UserEntity =
        localUserDataSource.fetchUser()
}