package com.halill.domain.features.auth.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.auth.repository.SaveUserNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserNameUseCase @Inject constructor(
    private val saveUserNameRepository: SaveUserNameRepository
) : UseCase<String, Flow<Boolean>>() {

    override suspend fun execute(data: String): Flow<Boolean> =
        saveUserNameRepository.saveUserName(data)
}