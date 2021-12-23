package com.halill.domain.auth.usecase

import com.halill.domain.auth.parameter.LoginParameter
import com.halill.domain.auth.repository.LoginRepository
import com.halill.domain.base.UseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): UseCase<LoginParameter, Unit>() {
    override suspend fun execute(data: LoginParameter) {
        loginRepository.login(data)
    }
}