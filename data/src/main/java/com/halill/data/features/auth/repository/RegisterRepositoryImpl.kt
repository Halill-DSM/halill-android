package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.entity.UserData
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.features.auth.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource
): RegisterRepository {
    override suspend fun register(parameter: RegisterParameter) {
        val user = UserData("김재원", parameter.userEntity.email)
        localAuthDataSource.saveUser(user)
    }
}