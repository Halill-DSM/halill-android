package com.halill.data.features.auth.datasource.local

import com.halill.domain.features.auth.entity.UserEntity

interface LocalUserDataSource {

    fun fetchUser(): UserEntity
}