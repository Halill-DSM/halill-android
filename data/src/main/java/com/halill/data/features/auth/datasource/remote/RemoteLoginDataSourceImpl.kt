package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.halill.domain.exception.LoginFailedException
import com.halill.domain.features.auth.param.LoginParam
import javax.inject.Inject

class RemoteLoginDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RemoteLoginDataSource {
    override fun login(loginParam: LoginParam) {
        auth.signInWithEmailAndPassword(loginParam.email, loginParam.password)
            .addOnCompleteListener { task ->
                if(!task.isSuccessful) {
                    throw LoginFailedException()
                }
            }
    }
}