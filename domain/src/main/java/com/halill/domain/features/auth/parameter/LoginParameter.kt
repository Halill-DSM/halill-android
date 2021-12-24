package com.halill.domain.features.auth.parameter

import com.halill.domain.features.auth.entity.User

data class LoginParameter(val user: User, val password: String)