package com.halill.data.features.auth.entity

import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.auth.parameter.LoginParameter

data class UserData(val name: String, val email: String)

fun UserData.toEntity() =
    User(
        name, email
    )
