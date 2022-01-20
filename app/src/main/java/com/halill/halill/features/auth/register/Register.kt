package com.halill.halill.features.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.features.auth.login.IdTextField
import com.halill.halill.features.auth.login.PasswordTextField
import com.halill.halill.features.auth.register.viewmodel.RegisterViewModel

@Composable
fun Register(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val focusRequester = FocusRequester()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val emailText = viewModel.email.collectAsState()
        val emailLabel = "이메일을 입력해주세요"
        IdTextField(
            focusRequester = focusRequester,
            text = emailText,
            label = emailLabel,
            doOnValueChange = {
                viewModel.setEmail(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val passwordText = viewModel.password.collectAsState()
        val passwordLabel = "비밀번호를 입력해주세요"
        PasswordTextField(
            focusRequester = focusRequester,
            text = passwordText,
            label = passwordLabel,
            doOnValueChange = {
                viewModel.setPassword(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val checkPasswordText = viewModel.checkPassword.collectAsState()
        val checkPasswordLabel = "비밀번호를 한번 더 입력해주세요"
        PasswordTextField(
            focusRequester = focusRequester,
            text = checkPasswordText,
            label = checkPasswordLabel,
            doOnValueChange = {
                viewModel.setCheckPassword(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val nameText = viewModel.name.collectAsState()
        val nameLabel = "이름을 입력해주세요"
        IdTextField(
            focusRequester = focusRequester,
            text = nameText,
            label = nameLabel,
            doOnValueChange = {
                viewModel.setName(it)
            },
            imeAction = ImeAction.Done
        )
    }
}