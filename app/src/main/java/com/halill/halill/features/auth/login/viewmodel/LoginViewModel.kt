package com.halill.halill.features.auth.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.InternetErrorException
import com.halill.domain.features.auth.exception.WrongIdException
import com.halill.domain.features.auth.parameter.LoginParameter
import com.halill.domain.features.auth.usecase.LoginUseCase
import com.halill.halill.features.auth.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val loginState = MutableLiveData<LoginState>()

    fun login() {
        if(!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty())
        viewModelScope.launch {
            try {
                loginUseCase.execute(LoginParameter(email.value!!, password.value!!))
                loginState.value = LoginState.FinishState
            } catch (e: WrongIdException) {
                loginState.value = LoginState.WrongIdState
            } catch (e: InternetErrorException) {
                loginState.value = LoginState.InternetExceptionState
            }

        }
    }

}