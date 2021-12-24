package com.halill.data.features.auth.dto.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(@SerializedName("access_token") val accessToken: String)