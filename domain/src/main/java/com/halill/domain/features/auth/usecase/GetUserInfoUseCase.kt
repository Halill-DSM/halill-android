package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import kotlinx.coroutines.flow.Flow

class GetUserInfoUseCase(private val userInfoRepository: GetUserInfoRepository): UseCase<Unit, Flow<User>>() {
    override suspend fun execute(data: Unit): Flow<User> =
        userInfoRepository.getUserInfo()
}