package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.repository.SaveUserNameRepository
import javax.inject.Inject

class SaveUserNameUseCase @Inject constructor(
    private val saveUserNameRepository: SaveUserNameRepository
) : UseCase<String, Unit>() {

    override suspend fun execute(data: String) {
        saveUserNameRepository.saveUserName(data)
    }
}