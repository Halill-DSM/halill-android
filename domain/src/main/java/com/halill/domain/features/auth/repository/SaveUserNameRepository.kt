package com.halill.domain.features.auth.repository

import kotlinx.coroutines.flow.Flow

interface SaveUserNameRepository {

    fun saveUserName(name: String): Flow<Boolean>
}