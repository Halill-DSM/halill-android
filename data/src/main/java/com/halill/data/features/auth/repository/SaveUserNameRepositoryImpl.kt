package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.domain.features.auth.repository.SaveUserNameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserNameRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : SaveUserNameRepository {

    override fun saveUserName(name: String): Flow<Boolean> =
        localUserDataSource.saveUserName(name)
}