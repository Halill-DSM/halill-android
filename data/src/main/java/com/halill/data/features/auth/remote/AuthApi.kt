package com.halill.data.features.auth.remote

import com.halill.data.features.auth.dto.request.LoginRequest
import com.halill.data.features.auth.dto.request.RefreshTokenRequest
import com.halill.data.features.auth.dto.request.RegisterRequest
import com.halill.data.features.auth.dto.response.LoginResponse
import com.halill.data.features.auth.dto.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {
    @POST("/signup")
    suspend fun register(@Body request: RegisterRequest)

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @PUT("/login")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): RefreshTokenResponse
}