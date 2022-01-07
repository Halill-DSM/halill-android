package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todo.repository.GetUserInfoRepository

class GetUserInfoUseCase(private val userInfoRepository: GetUserInfoRepository): UseCase<Unit, User>() {
    override suspend fun execute(data: Unit): User =
        userInfoRepository.getUserInfo()
}