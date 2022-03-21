package com.halill.data.features.auth.datasource.local

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.halill.data.local.datastorage.LocalStorage
import com.halill.data.util.createFlowToCheckTaskCallbackIsSuccess
import com.halill.domain.features.auth.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val localStorage: LocalStorage
) : LocalUserDataSource {

    override suspend fun fetchUser(): Flow<UserEntity> {
        val currentUser = auth.currentUser
        return flow {
            emit(UserEntity(currentUser?.displayName ?: "", currentUser?.email ?: ""))
        }
    }

    override suspend fun deleteUser() {
        localStorage.saveIsNotLoginState()
    }

    override suspend fun fetchIsLoginState(): Flow<Boolean> =
        localStorage.isLoginState()

    override fun saveUserName(name: String): Flow<Boolean> =
        createSaveNameCallback(name)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createSaveNameCallback(param: String): Flow<Boolean> {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = param
        }
        return user!!.updateProfile(profileUpdates).createFlowToCheckTaskCallbackIsSuccess()
    }


}