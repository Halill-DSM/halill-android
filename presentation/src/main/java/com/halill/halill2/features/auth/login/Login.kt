package com.halill.halill2.features.auth.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill2.R
import com.halill.halill2.base.EventFlow
import com.halill.halill2.base.observeWithLifecycle
import com.halill.halill2.features.auth.IdTextField
import com.halill.halill2.features.auth.PasswordTextField
import com.halill.halill2.ui.theme.Teal200
import com.halill.halill2.ui.theme.Teal900
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun Login(
    scaffoldState: ScaffoldState,
    navController: NavController,
    darkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val backgroundColor = if (darkTheme) Color.Black else Teal200
    handleViewEffect(
        scaffoldState = scaffoldState,
        navController = navController,
        effect = viewModel.loginViewEffect
    )
    Scaffold(scaffoldState = scaffoldState) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        ) {
            ConstraintLayout(decoupledConstraints(), modifier = Modifier.fillMaxSize()) {
                LoginTitle()
                LoginComment()
                LoginIluImage()
                LoginLayout(navController, viewModel)
            }
        }
    }
}

@OptIn(InternalCoroutinesApi::class)
@Composable
private fun handleViewEffect(
    scaffoldState: ScaffoldState,
    navController: NavController,
    effect: EventFlow<LoginViewEffect>
) {
    val wrongComment = stringResource(id = R.string.wrong_id_comment)
    val internetErrorComment = stringResource(id = R.string.internet_error_comment)
    val emptyComment = stringResource(id = R.string.login_empty_comment)

    effect.observeWithLifecycle(action = {
        when (it) {
            is LoginViewEffect.WrongId
            -> scaffoldState.snackbarHostState.showSnackbar(
                wrongComment,
                duration = SnackbarDuration.Short
            )
            is LoginViewEffect.InternetError -> scaffoldState.snackbarHostState.showSnackbar(
                internetErrorComment,
                duration = SnackbarDuration.Short
            )
            is LoginViewEffect.NotDoneInput ->
                scaffoldState.snackbarHostState.showSnackbar(
                    emptyComment,
                    duration = SnackbarDuration.Short
                )
            is LoginViewEffect.FinishLogin -> navController.popBackStack()
        }
    })
    BackPressHandle()
}

@Composable
private fun BackPressHandle() {
    val backHandlingEnabled by remember { mutableStateOf(true) }
    val activity = (LocalContext.current as? Activity)
    BackHandler(backHandlingEnabled) {
        activity?.finish()
    }
}

private fun decoupledConstraints(): ConstraintSet =
    ConstraintSet {
        val titleImage = createRefFor(LoginViews.TitleImageView)
        val commentImage = createRefFor(LoginViews.CommentImageView)
        val loginIluImage = createRefFor(LoginViews.ContentImage)
        val loginConstraintLayout = createRefFor(LoginViews.LoginConstraintLayout)
        constrain(titleImage) {
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(parent.start, margin = 20.dp)
        }
        constrain(commentImage) {
            top.linkTo(titleImage.bottom, margin = 10.dp)
            start.linkTo(parent.start, margin = 20.dp)
        }
        constrain(loginIluImage) {
            top.linkTo(titleImage.bottom)
            end.linkTo(parent.end, margin = 10.dp)
        }
        constrain(loginConstraintLayout) {
            bottom.linkTo(parent.bottom)
        }
    }

@Composable
fun LoginTitle() {
    Image(
        painter = painterResource(id = R.drawable.ic_halill_title),
        contentDescription = "HalIllTitle",
        modifier = Modifier.layoutId(LoginViews.TitleImageView)
    )
}

@Composable
fun LoginComment() {
    Image(
        painter = painterResource(id = R.drawable.ic_halill_comment),
        contentDescription = "HalIllComment",
        modifier = Modifier.layoutId(LoginViews.CommentImageView)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginIluImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_login_illu),
        contentDescription = "LoginIluImage",
        modifier = Modifier.layoutId(LoginViews.ContentImage)
    )
}

@Composable
fun LoginLayout(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val state = loginViewModel.state.collectAsState().value
    ConstraintLayout(
        loginLayoutConstraint(),
        modifier = Modifier
            .layoutId(LoginViews.LoginConstraintLayout)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = MaterialTheme.colors.background)
    ) {
        val coroutineScope = rememberCoroutineScope()

        val emailLabel = "이메일"
        IdTextField(
            text = state.email,
            label = emailLabel,
            doOnValueChange = {
                loginViewModel.setEmail(it)
            },
            imeAction = ImeAction.Next
        )

        val passwordLabel = "비밀번호"
        PasswordTextField(
            text = state.password,
            label = passwordLabel,
            doOnValueChange = {
                loginViewModel.setPassword(it)
            },
            imeAction = ImeAction.Done
        )


        LoginButton(
            loginState = state,
            onLoginButtonClick = { isDoneInput ->
                if (isDoneInput) {
                    coroutineScope.launch {
                        loginViewModel.login()
                    }
                } else {
                    coroutineScope.launch {
                        loginViewModel.notDoneInput()
                    }
                }
            }
        )
        AskRegisterText()
        StartRegisterButton(doOnClick = {
            navController.navigate("register")
            loginViewModel.setStateInitial()
        })
    }
}

private fun loginLayoutConstraint(): ConstraintSet =
    ConstraintSet {
        val idTextField = createRefFor(LoginLayoutViews.IdTextField)
        val passwordTextField = createRefFor(LoginLayoutViews.PasswordField)
        val loginButton = createRefFor(LoginLayoutViews.LoginButtonId)
        val askRegisterText = createRefFor(LoginLayoutViews.AskRegisterText)
        val registerButton = createRefFor(LoginLayoutViews.RegisterButton)
        constrain(idTextField) {
            top.linkTo(parent.top, margin = 35.dp)
            start.linkTo(parent.start, margin = 15.dp)
            end.linkTo(parent.end, margin = 15.dp)
        }
        constrain(passwordTextField) {
            top.linkTo(idTextField.bottom, margin = 10.dp)
            start.linkTo(parent.start, margin = 15.dp)
            end.linkTo(parent.end, margin = 15.dp)
        }
        constrain(loginButton) {
            top.linkTo(passwordTextField.bottom, margin = 15.dp)
            start.linkTo(passwordTextField.start)
            end.linkTo(passwordTextField.end)
            width = Dimension.fillToConstraints
        }
        constrain(askRegisterText) {
            top.linkTo(loginButton.bottom, margin = 25.dp)
            start.linkTo(loginButton.start)
            bottom.linkTo(parent.bottom, margin = 25.dp)
        }
        constrain(registerButton) {
            top.linkTo(askRegisterText.top)
            end.linkTo(loginButton.end)
            bottom.linkTo(askRegisterText.bottom)
        }
    }

@Composable
fun LoginButton(
    loginState: LoginState,
    onLoginButtonClick: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Button(
        onClick = {
            focusManager.clearFocus()
            onLoginButtonClick(doneInput(loginState))
        },
        colors = buttonColors(
            backgroundColor = if (loginState.isLoading) Color.Gray else Teal900,
            contentColor = Color.White
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .size(250.dp, 40.dp)
            .layoutId(LoginLayoutViews.LoginButtonId)
    ) {
        val text =
            if (loginState.isLoading) stringResource(id = R.string.loading_comment)
            else stringResource(id = R.string.login)
        Text(text = text)
    }
}

private fun doneInput(state: LoginState) =
    state.email.isNotBlank() && state.password.isNotBlank() && state.doneInput

@Composable
private fun AskRegisterText() {
    Text(
        text = stringResource(id = R.string.ask_register),
        Modifier
            .layoutId(LoginLayoutViews.AskRegisterText)
            .wrapContentSize(),
        fontSize = 15.sp
    )
}

@Composable
fun StartRegisterButton(doOnClick: () -> Unit) {
    TextButton(
        onClick = { doOnClick() },
        Modifier
            .layoutId(LoginLayoutViews.RegisterButton)
            .wrapContentSize(),
    ) {
        Text(text = stringResource(id = R.string.register), fontSize = 15.sp, color = Teal900)
    }
}