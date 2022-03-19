package com.halill.domain.features.auth.repository

interface LogoutRepository {

    suspend fun logout()
}