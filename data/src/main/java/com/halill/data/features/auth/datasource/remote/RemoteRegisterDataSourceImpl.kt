package com.halill.data.features.auth.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.halill.domain.features.auth.param.RegisterParam
import javax.inject.Inject

class RemoteRegisterDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : RemoteRegisterDataSource {

    override suspend fun register(registerParam: RegisterParam) {
        auth.createUserWithEmailAndPassword(registerParam.email, registerParam.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setUserName(registerParam.name)
                }
            }
    }

    private fun setUserName(userName: String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = userName
        }

        user!!.updateProfile(profileUpdates)
    }
}