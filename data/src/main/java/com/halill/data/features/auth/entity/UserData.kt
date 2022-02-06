package com.halill.data.features.auth.entity

import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class UserData(val name: String, val email: String)

fun UserData.toEntity() =
    UserEntity(
        name, email
    )

fun Flow<UserData>.toEntity() =
    this.map { it.toEntity() }