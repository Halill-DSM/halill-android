package com.halill.halill.features.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.features.auth.IdTextField
import com.halill.halill.features.auth.PasswordTextField
import com.halill.halill.features.auth.register.viewmodel.RegisterViewModel

@Composable
fun Register(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusRequester = remember { FocusRequester() }
        val emailText = viewModel.email.collectAsState()
        val emailLabel = "이메일을 입력해주세요"
        IdTextField(
            text = emailText,
            label = emailLabel,
            layoutId = "register_email_tf",
            doOnValueChange = {
                viewModel.setEmail(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val passwordText = viewModel.password.collectAsState()
        val passwordLabel = "비밀번호를 입력해주세요"
        PasswordTextField(
            text = passwordText,
            label = passwordLabel,
            layoutId = "register_password_tf",
            doOnValueChange = {
                viewModel.setPassword(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val checkPasswordText = viewModel.checkPassword.collectAsState()
        val checkPasswordLabel = "비밀번호를 한번 더 입력해주세요"
        PasswordTextField(
            text = checkPasswordText,
            label = checkPasswordLabel,
            layoutId = "register_check_password_tf",
            doOnValueChange = {
                viewModel.setCheckPassword(it)
            },
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(25.dp))
        val nameText = viewModel.name.collectAsState()
        val nameLabel = "이름을 입력해주세요"
        IdTextField(
            text = nameText,
            label = nameLabel,
            layoutId = "register_name_tf",
            doOnValueChange = {
                viewModel.setName(it)
            },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
    }
}