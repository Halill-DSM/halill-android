package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.domain.features.auth.repository.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
) : LogoutRepository {
    
    override suspend fun logout() {
        localUserDataSource.deleteUser()
    }
}