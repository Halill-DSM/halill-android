package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.repository.LogoutRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val logoutRepository: LogoutRepository
) : UseCase<Unit, Unit>() {

    override suspend fun execute(data: Unit) {
        logoutRepository.logout()
    }
}