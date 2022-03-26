package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val fetchUserInfoRepository: FetchUserInfoRepository
) : UseCase<Unit, Flow<Boolean>>() {

    override suspend fun execute(data: Unit): Flow<Boolean> =
        fetchUserInfoRepository.isLoginState()
}