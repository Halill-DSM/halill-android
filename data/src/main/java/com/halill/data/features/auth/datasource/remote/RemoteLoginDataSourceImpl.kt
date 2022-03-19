package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.halill.domain.exception.LoginFailedException
import com.halill.domain.features.auth.param.LoginParam
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RemoteLoginDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RemoteLoginDataSource {

    override suspend fun login(loginParam: LoginParam) {
        CoroutineScope(Dispatchers.IO).runCatching {
            createLoginCallback(loginParam).collect { isSuccess ->
                if (!isSuccess) {
                    throw LoginFailedException()
                }
            }
        }.onFailure {
            it
            throw LoginFailedException()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createLoginCallback(loginParam: LoginParam): Flow<Boolean> =
        callbackFlow {
            auth.signInWithEmailAndPassword(loginParam.email, loginParam.password)
                .addOnCompleteListener {
                    trySendBlocking(it.isSuccessful)
                    close()
                }
            awaitClose()
        }
}