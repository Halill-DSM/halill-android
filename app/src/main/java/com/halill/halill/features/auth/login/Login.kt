package com.halill.halill.features.auth.login

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
import com.halill.halill.R
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.features.auth.IdTextField
import com.halill.halill.features.auth.PasswordTextField
import com.halill.halill.features.auth.login.viewmodel.LoginViewModel
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal200
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun Login(
    navController: NavController,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val backgroundColor = if (darkTheme) Color.Black else Teal200
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
                LoginLayout(navController)
            }
        }
    }
    EventHandle(navController = navController)
}

@Composable
private fun EventHandle(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val wrongComment = stringResource(id = R.string.wrong_id_comment)
    val internetErrorComment = stringResource(id = R.string.internet_error_comment)
    viewModel.loginViewEffect.observeWithLifecycle(action = {
        when (it) {
            is LoginEvent.WrongId -> scaffoldState.snackbarHostState.showSnackbar(
                wrongComment,
                duration = SnackbarDuration.Short
            )


            is LoginEvent.InternetError -> scaffoldState.snackbarHostState.showSnackbar(
                internetErrorComment,
                duration = SnackbarDuration.Short
            )

            is LoginEvent.FinishLogin -> navController.popBackStack()
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
fun LoginLayout(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    ConstraintLayout(
        loginLayoutConstraint(),
        modifier = Modifier
            .layoutId(LoginViews.LoginConstraintLayout)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = Color.White)
    ) {
        val emailText = loginViewModel.email.collectAsState()
        val emailLabel = "이메일"
        IdTextField(
            text = emailText,
            label = emailLabel,
            doOnValueChange = {
                loginViewModel.setEmail(it)
            },
            imeAction = ImeAction.Next
        )
        val passwordText = loginViewModel.password.collectAsState()
        val passwordLabel = "비밀번호"
        PasswordTextField(
            text = passwordText,
            label = passwordLabel,
            doOnValueChange = {
                loginViewModel.setPassword(it)
            },
            imeAction = ImeAction.Done
        )
        LoginButton()
        AskRegisterText()
        StartRegisterButton(navController)
    }
}

private fun loginLayoutConstraint(): ConstraintSet =
    ConstraintSet {
        val idTextField = createRefFor(LoginLayoutViews.IdTextField)
        val passwordTextField = createRefFor(LoginLayoutViews.PasswordField)
        val loginButton = createRefFor(LoginLayoutViews.LoginButton)
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
fun LoginButton(loginViewModel: LoginViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    val loginState = loginViewModel.loginState.collectAsState()

    val focusManager = LocalFocusManager.current
    val emptyComment = stringResource(id = R.string.login_empty_comment)
    Button(
        onClick = {
            focusManager.clearFocus()
            if (loginState.value is LoginState.DoneInputState) {
                loginViewModel.login()
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        emptyComment,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        colors = buttonColors(
            backgroundColor = if (loginState.value is LoginState.DoneInputState) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .layoutId(LoginLayoutViews.LoginButton)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Text(text = stringResource(id = R.string.login))
    }

}

@Composable
fun AskRegisterText() {
    Text(
        text = stringResource(id = R.string.ask_register),
        Modifier
            .layoutId(LoginLayoutViews.AskRegisterText)
            .wrapContentSize(),
        fontSize = 15.sp
    )
}

@Composable
fun StartRegisterButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate("register") },
        Modifier
            .layoutId(LoginLayoutViews.RegisterButton)
            .wrapContentSize(),
    ) {
        Text(text = stringResource(id = R.string.register), fontSize = 15.sp, color = Teal900)
    }
}