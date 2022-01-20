package com.halill.halill.features.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.features.auth.login.PasswordTextField
import com.halill.halill.features.auth.register.viewmodel.RegisterViewModel

@Composable
fun Register(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val passwordFocusRequester = FocusRequester()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val passwordText = viewModel.password.collectAsState()
        PasswordTextField(
            passwordFocusRequester = passwordFocusRequester,
            text = passwordText,
            label = "비밀번호를 입력해주세요",
            doOnValueChange = {
                viewModel.setPassword(it)
            }
        )
    }
}