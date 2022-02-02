package com.halill.domain.features.auth.repository

interface CheckLoginRepository {
    suspend fun checkLogin()
}