package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.repository.CheckLoginRepository
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val checkLoginRepository: CheckLoginRepository
): UseCase<Unit, Unit>() {
    override suspend fun execute(data: Unit) {
        checkLoginRepository.checkLogin()
    }

}