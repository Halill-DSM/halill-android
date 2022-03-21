package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.param.RegisterParam
import com.halill.domain.features.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
): UseCase<RegisterParam, Unit>() {

    override suspend fun execute(data: RegisterParam) {
        registerRepository.register(data)
    }
}