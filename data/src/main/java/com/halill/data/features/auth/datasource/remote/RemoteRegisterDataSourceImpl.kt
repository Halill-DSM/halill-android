package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.halill.data.util.createFlowToCheckTaskCallbackIsSuccess
import com.halill.domain.exception.RegisterFailedException
import com.halill.domain.features.auth.param.RegisterParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
        auth.createUserWithEmailAndPassword(param.email, param.password)
            .createFlowToCheckTaskCallbackIsSuccess()
}