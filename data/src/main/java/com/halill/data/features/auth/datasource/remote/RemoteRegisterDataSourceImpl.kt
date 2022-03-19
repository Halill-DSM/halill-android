package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.halill.domain.exception.LoginFailedException
import com.halill.domain.exception.RegisterFailedException
import com.halill.domain.features.auth.param.LoginParam
import com.halill.domain.features.auth.param.RegisterParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RemoteRegisterDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RemoteRegisterDataSource {

    override suspend fun register(registerParam: RegisterParam) {
        CoroutineScope(Dispatchers.IO).runCatching {
            createRegisterCallback(registerParam).collect { isSuccess ->
                if (!isSuccess) {
                    throw RegisterFailedException()
                }
            }
        }.onFailure {
            throw RegisterFailedException()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createRegisterCallback(param: RegisterParam): Flow<Boolean> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener {
                    trySendBlocking(it.isSuccessful)
                    close()
                }
            awaitClose()
        }
}