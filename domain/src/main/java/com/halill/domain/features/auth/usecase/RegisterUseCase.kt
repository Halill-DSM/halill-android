package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.features.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
): UseCase<RegisterParameter, Unit>() {
    override suspend fun execute(data: RegisterParameter) {
        registerRepository.register(data)
    }
}