package com.halill.halill2.features.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill2.R
import com.halill.halill2.base.EventFlow
import com.halill.halill2.base.observeWithLifecycle
import com.halill.halill2.features.auth.IdTextField
import com.halill.halill2.features.auth.PasswordTextField
import com.halill.halill2.features.auth.login.LoginLayoutViews
import com.halill.halill2.theme.Teal900
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun Register(
    scaffoldState: ScaffoldState,
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val scope = rememberCoroutineScope()

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
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                RegisterEmailTextField(
                    state,
                    doOnEmailTextChange = { email -> viewModel.setEmail(email) }
                )

                RegisterPasswordTextField(
                    state,
                    doOnPasswordTextChange = { password -> viewModel.setPassword(password) }
                )

                RegisterCheckPasswordTextField(
                    state,
                    doOnCheckPasswordTextChange = { checkPassword ->
                        viewModel.setCheckPassword(
                            checkPassword
                        )
                    }
                )

                val emptyComment = stringResource(id = R.string.login_empty_comment)
                RegisterButton(
                    state = state,
                    doOnRegisterButtonClick = {
                        viewModel.register()
                    },
                    doOnClickWhenEmpty = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                emptyComment,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                )
            }
        })
    val event = viewModel.registerViewEffect
    handleViewEffect(scaffoldState = scaffoldState, navController = navController, event = event)
}

@OptIn(InternalCoroutinesApi::class)
@Composable
private fun handleViewEffect(
    scaffoldState: ScaffoldState,
    navController: NavController,
    event: EventFlow<RegisterViewEffect>
) {
    val scope = rememberCoroutineScope()
    val successComment = stringResource(id = R.string.success_register_comment)
    val failRegisterComment = stringResource(id = R.string.fail_register_comment)
    event.observeWithLifecycle(action = {
        when (it) {
            is RegisterViewEffect.FinishRegister -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        successComment,
                        duration = SnackbarDuration.Short
                    )
                }
                navController.popBackStack()
            }
            is RegisterViewEffect.FailRegister -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        failRegisterComment,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    })
}

@Composable
fun RegisterEmailTextField(
    state: RegisterState,
    doOnEmailTextChange: (String) -> Unit
) {
    val emailText = state.email
    val emailLabel = "???????????? ??????????????????"

    Spacer(modifier = Modifier.height(25.dp))

    IdTextField(
        text = emailText,
        label = emailLabel,
        layoutId = "register_email_tf",
        doOnValueChange = {
            doOnEmailTextChange(it)
        },
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterPasswordTextField(
    state: RegisterState,
    doOnPasswordTextChange: (String) -> Unit
) {
    val passwordText = state.password
    val passwordLabel = "??????????????? ??????????????????"

    Spacer(modifier = Modifier.height(25.dp))

    PasswordTextField(
        text = passwordText,
        label = passwordLabel,
        layoutId = "register_password_tf",
        doOnValueChange = {
            doOnPasswordTextChange(it)
        },
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterCheckPasswordTextField(
    state: RegisterState,
    doOnCheckPasswordTextChange: (String) -> Unit
) {
    val checkPasswordText = state.checkPassword
    val checkPasswordLabel = "??????????????? ?????? ??? ??????????????????"

    Spacer(modifier = Modifier.height(25.dp))

    PasswordTextField(
        text = checkPasswordText,
        label = checkPasswordLabel,
        layoutId = "register_check_password_tf",
        doOnValueChange = {
            doOnCheckPasswordTextChange(it)
        },
        isError = checkPasswordText.isNotEmpty() && !state.passwordIsSameWithCheck(),
        imeAction = ImeAction.Next
    )
}

@Composable
fun RegisterButton(
    state: RegisterState,
    doOnRegisterButtonClick: () -> Unit,
    doOnClickWhenEmpty: () -> Unit
) {
    Spacer(modifier = Modifier.height(25.dp))
    Button(
        onClick = {
            if (state.doneInput()) {
                doOnRegisterButtonClick()
            } else {
                doOnClickWhenEmpty()
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (state.doneInput() && !state.isLoading) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .size(250.dp, 40.dp)
            .layoutId(LoginLayoutViews.LoginButtonId)
    ) {
        val text =
            if (state.isLoading) stringResource(id = R.string.loading_comment)
            else stringResource(id = R.string.register)
        Text(text = text)
    }
}

private fun RegisterState.doneInput(): Boolean =
    isNotEmpty() && passwordIsSameWithCheck()

private fun RegisterState.isNotEmpty(): Boolean =
    email.isNotEmpty() && password.isNotEmpty() && checkPassword.isNotEmpty()

private fun RegisterState.passwordIsSameWithCheck(): Boolean =
    password == checkPassword