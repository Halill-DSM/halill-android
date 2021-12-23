package com.halill.domain.auth.parameter

import com.halill.domain.auth.entity.User

data class RegisterParameter (val user: User, val password: String)