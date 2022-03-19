package com.halill.data.features.auth.datasource.local

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.halill.data.local.datastorage.LocalStorage
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val localStorage: LocalStorage
) : LocalUserDataSource {

    override suspend fun fetchUser(): Flow<UserEntity> {
        val currentUser = auth.currentUser
        return localStorage.isLoginState().map {
            if (currentUser != null && it) {
                UserEntity(currentUser.displayName ?: "", currentUser.email!!)
            } else {
                throw NotLoginException()
            }
        }
    }

    override suspend fun deleteUser() {
        val user = auth.currentUser!!
        user.delete()
        localStorage.saveIsNotLoginState()
    }

    override fun saveUserName(name: String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
    }

}