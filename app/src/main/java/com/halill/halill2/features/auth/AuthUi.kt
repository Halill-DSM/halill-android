package com.halill.halill2.features.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.halill.halill2.features.auth.login.LoginLayoutViews
import com.halill.halill2.ui.theme.Gray200

@Composable
fun IdTextField(
    text: String,
    label: String,
    layoutId: Any = LoginLayoutViews.IdTextField,
    doOnValueChange: (text: String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Email,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    TextField(value = text,
        onValueChange = {
            doOnValueChange(it)
        },
        label = { Text(label) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
        modifier = loginTextFieldModifier
            .layoutId(layoutId)
    )
}

@Composable
fun PasswordTextField(
    text: String,
    layoutId: Any = LoginLayoutViews.PasswordField,
    label: String,
    doOnValueChange: (text: String) -> Unit,
    isError: Boolean = false,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = loginTextFieldModifier
            .layoutId(layoutId)
    ) {
        TextField(value = text,
            onValueChange = {
                doOnValueChange(it)
            },
            label = { Text(label) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = isError,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisibility) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = icon, contentDescription = "password visible")
                }
            }
        )
        if (isError) {
            val errorMessage = "비밀번호가 일치하지 않습니다"
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

private val loginTextFieldModifier by lazy {
    Modifier
        .clip(RoundedCornerShape(30.dp))
        .border(
            width = 1.dp,
            color = Gray200,
            shape = RoundedCornerShape(30.dp)
        )
}