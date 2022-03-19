package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.domain.features.auth.repository.SaveUserNameRepository
import javax.inject.Inject

class SaveUserNameRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : SaveUserNameRepository {

    override fun saveUserName(name: String) {
        localUserDataSource.saveUserName(name)
    }
}