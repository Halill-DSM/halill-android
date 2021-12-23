package com.halill.domain.login.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.login.parameter.RegisterParameter
import com.halill.domain.login.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
): UseCase<RegisterParameter, Unit>() {
    override suspend fun execute(data: RegisterParameter) {
        registerRepository.register(data)
    }
}