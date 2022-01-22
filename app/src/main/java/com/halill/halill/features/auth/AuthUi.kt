package com.halill.halill.features.auth

import androidx.compose.foundation.border
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
import com.halill.halill.features.auth.login.LoginLayoutViews
import com.halill.halill.ui.theme.Gray200

@Composable
fun IdTextField(
    text: State<String>,
    label: String,
    layoutId: Any = LoginLayoutViews.IdTextField,
    doOnValueChange: (text: String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Email,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    TextField(value = text.value,
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
    text: State<String>,
    layoutId: Any = LoginLayoutViews.PasswordField,
    label: String,
    doOnValueChange: (text: String) -> Unit,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    TextField(value = text.value, onValueChange = {
        doOnValueChange(it)
    }, label = { Text(label) },
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
        modifier = loginTextFieldModifier
            .layoutId(layoutId),
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