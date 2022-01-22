package com.halill.halill.features.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.features.auth.IdTextField
import com.halill.halill.features.auth.PasswordTextField
import com.halill.halill.features.auth.login.LoginLayoutViews
import com.halill.halill.features.auth.login.scaffoldState
import com.halill.halill.features.auth.register.model.RegisterState
import com.halill.halill.features.auth.register.viewmodel.RegisterViewModel
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun Register(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                backgroundColor = Teal700,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RegisterEmailTextField(viewModel = viewModel)
                val passwordText = viewModel.password.collectAsState()
                RegisterPasswordTextField(viewModel = viewModel, password = passwordText)
                RegisterCheckPasswordTextField(viewModel = viewModel, password = passwordText)
                RegisterNameTextField(viewModel = viewModel)
                RegisterButton(viewModel = viewModel)
            }
        })

}

@Composable
fun RegisterEmailTextField(viewModel: RegisterViewModel) {
    val emailText = viewModel.email.collectAsState()
    val emailLabel = "이메일을 입력해주세요"
    Spacer(modifier = Modifier.height(25.dp))
    IdTextField(
        text = emailText,
        label = emailLabel,
        layoutId = "register_email_tf",
        doOnValueChange = {
            viewModel.setEmail(it)
        },
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterPasswordTextField(viewModel: RegisterViewModel, password: State<String>) {
    val passwordLabel = "비밀번호를 입력해주세요"
    Spacer(modifier = Modifier.height(25.dp))
    PasswordTextField(
        text = password,
        label = passwordLabel,
        layoutId = "register_password_tf",
        doOnValueChange = {
            viewModel.setPassword(it)
        },
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterCheckPasswordTextField(viewModel: RegisterViewModel, password: State<String>) {
    val checkPasswordText = viewModel.checkPassword.collectAsState()
    val checkPasswordLabel = "비밀번호를 한번 더 입력해주세요"
    Spacer(modifier = Modifier.height(25.dp))
    PasswordTextField(
        text = checkPasswordText,
        label = checkPasswordLabel,
        layoutId = "register_check_password_tf",
        doOnValueChange = {
            viewModel.setCheckPassword(it)
        },
        isError = checkPasswordText.value.checkPassword(password.value),
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterNameTextField(viewModel: RegisterViewModel) {
    val nameText = viewModel.name.collectAsState()
    val nameLabel = "이름을 입력해주세요"
    Spacer(modifier = Modifier.height(25.dp))
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

@Composable
fun RegisterButton(viewModel: RegisterViewModel) {
    val scope = rememberCoroutineScope()
    val registerState = viewModel.registerState
    val emptyComment = stringResource(id = R.string.login_empty_comment)
    Spacer(modifier = Modifier.height(25.dp))
    Button(
        onClick = {
            if (registerState.value is RegisterState.DoneInputState) {
                viewModel.register()
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        emptyComment,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (registerState.value is RegisterState.DoneInputState) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .layoutId(LoginLayoutViews.LoginButton)
            .width(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Text(text = stringResource(id = R.string.register))
    }
}

fun String.checkPassword(password: String): Boolean =
    this.isNotEmpty() && this != password