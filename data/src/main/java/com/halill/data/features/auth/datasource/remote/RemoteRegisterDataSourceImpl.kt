package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.halill.domain.features.auth.param.RegisterParam
import javax.inject.Inject

class RemoteRegisterDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
): RemoteRegisterDataSource {

    override suspend fun register(registerParam: RegisterParam) {
        auth.createUserWithEmailAndPassword(registerParam.email, registerParam.password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                } else {

                }
            }
    }
}