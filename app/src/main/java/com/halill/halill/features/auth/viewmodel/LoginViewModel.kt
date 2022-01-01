package com.halill.halill.features.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): ViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {

    }

    fun isIdAndPasswordFilled(): Boolean =
        !id.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
}