package com.halill.data.features.auth.dto.response

import com.google.gson.annotations.SerializedName
import com.halill.data.features.auth.entity.TokenData

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)

fun LoginResponse.toDataEntity() =
    TokenData(accessToken, refreshToken)