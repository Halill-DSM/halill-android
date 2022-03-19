package com.halill.data.features.auth.datasource.local

import com.google.firebase.auth.FirebaseAuth
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.entity.UserEntity
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LocalUserDataSource {

    override fun fetchUser(): UserEntity {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            return UserEntity(currentUser.displayName!!, currentUser.email!!)
        } else {
            throw NotLoginException()
        }
    }
}