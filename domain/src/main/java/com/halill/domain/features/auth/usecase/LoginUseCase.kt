package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.param.LoginParam
import com.halill.domain.features.auth.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<LoginParam, Unit>() {

    override suspend fun execute(data: LoginParam) {
        loginRepository.login(data)
    }
}