package com.halill.data.features.auth.datasource.remote

import com.halill.data.features.auth.dto.request.toRequest
import com.halill.data.features.auth.dto.response.LoginResponse
import com.halill.data.features.auth.remote.AuthApi
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.parameter.RegisterParameter
import com.halill.domain.exception.InternetErrorException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : RemoteAuthDataSource {
    override suspend fun login(parameter: LoginParameter): LoginResponse =
        try {
            authApi.login(parameter.toRequest())
        } catch (e: UnknownHostException) {
            throw InternetErrorException()
        }


    override suspend fun register(parameter: RegisterParameter) =
        try {
            authApi.register(parameter.toRequest())
        } catch (e: UnknownHostException) {
            throw InternetErrorException()
        }
}