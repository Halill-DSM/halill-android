package com.halill.domain.features.auth.parameter

import com.halill.domain.features.auth.entity.UserEntity

data class RegisterParameter (val userEntity: UserEntity, val password: String)