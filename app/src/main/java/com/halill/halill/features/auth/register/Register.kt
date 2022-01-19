package com.halill.halill.features.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import com.halill.halill.features.auth.login.PasswordTextField
import com.halill.halill.features.auth.register.viewmodel.RegisterViewModel

@Composable
fun Register(viewModel: RegisterViewModel = hiltViewModel()) {
    val passwordFocusRequester = FocusRequester()
    Column {
        val passwordText = viewModel.password.collectAsState()
        PasswordTextField(passwordFocusRequester = passwordFocusRequester, text = passwordText, doOnValueChange = {
            viewModel.setPassword(it)
        })
    }
}