package com.halill.data.features.auth.dto.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(@SerializedName("refresh_token") val refreshToken: String)