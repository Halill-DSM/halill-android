package com.halill.data.features.auth.repository

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import javax.inject.Inject

class FetchUserInfoRepositoryImpl @Inject constructor(
    
) : FetchUserInfoRepository {

    override suspend fun fetchUserInfo(): UserEntity {
        TODO("Not yet implemented")
    }
}