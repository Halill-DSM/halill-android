package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.remote.RemoteLoginDataSource
import com.halill.data.local.datastorage.LocalStorage
import com.halill.domain.features.auth.param.LoginParam
import com.halill.domain.features.auth.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val remoteLoginDataSource: RemoteLoginDataSource,
    private val localStorage: LocalStorage
) : LoginRepository{

    override suspend fun login(loginParam: LoginParam) {
        remoteLoginDataSource.login(loginParam)
        localStorage.saveIsLoginState()
    }
}