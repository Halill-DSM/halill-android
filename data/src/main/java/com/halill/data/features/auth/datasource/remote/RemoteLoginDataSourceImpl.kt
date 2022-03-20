package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.halill.data.util.createFlowToCheckTaskCallbackIsSuccess
import com.halill.domain.exception.LoginFailedException
import com.halill.domain.features.auth.param.LoginParam
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
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
            throw LoginFailedException()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createLoginCallback(loginParam: LoginParam): Flow<Boolean> =
        auth.signInWithEmailAndPassword(loginParam.email, loginParam.password)
            .createFlowToCheckTaskCallbackIsSuccess()
}