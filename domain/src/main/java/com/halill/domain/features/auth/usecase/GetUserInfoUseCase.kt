package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.repository.GetUserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userInfoRepository: GetUserInfoRepository
) : UseCase<Unit, Flow<UserEntity>>() {
    
    override suspend fun execute(data: Unit): Flow<UserEntity> =
        userInfoRepository.getUserInfo()
}