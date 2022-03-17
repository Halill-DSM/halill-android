package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(
    private val fetchUserInfoRepository: FetchUserInfoRepository
): UseCase<Unit, UserEntity>() {

    override suspend fun execute(data: Unit): UserEntity =
        fetchUserInfoRepository.fetchUserInfo()
}