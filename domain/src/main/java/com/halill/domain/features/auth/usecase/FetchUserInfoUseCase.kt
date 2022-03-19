package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(
    private val fetchUserInfoRepository: FetchUserInfoRepository
): UseCase<Unit, Flow<UserEntity>>() {

    override suspend fun execute(data: Unit): Flow<UserEntity> =
        fetchUserInfoRepository.fetchUserInfo()
}