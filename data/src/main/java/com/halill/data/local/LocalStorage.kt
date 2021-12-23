package com.halill.data.local

interface LocalStorage {
    fun saveToken(token: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String
}