package com.halill.halill.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.halill.halill.R
import com.halill.halill.features.auth.viewmodel.LoginViewModel
import com.halill.halill.ui.theme.HalIll_AndroidTheme
import com.halill.halill.ui.theme.Teal200
import com.halill.halill.ui.theme.Teal900

@Composable
fun Login(darkTheme: Boolean = isSystemInDarkTheme(), viewModel: LoginViewModel = hiltViewModel()) {
    val backgroundColor = if(darkTheme) Color.Black else Teal200
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        val titleImage = createRef()
        val commentImage = createRef()
        val loginConstraintLayout = createRef()
        Image(
            painter = painterResource(id = R.drawable.ic_halill_title),
            contentDescription = "HalIllTitle",
            modifier = Modifier.constrainAs(titleImage) {
                top.linkTo(parent.top, margin = 15.dp)
                start.linkTo(parent.start, margin = 15.dp)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_halill_comment),
            contentDescription = "HalIllComment",
            modifier = Modifier.constrainAs(commentImage) {
                top.linkTo(titleImage.bottom, margin = 10.dp)
                start.linkTo(parent.start, margin = 15.dp)
            }
        )
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .constrainAs(loginConstraintLayout) {
                bottom.linkTo(parent.bottom)
            }
            .background(color = Color.White)
        ) {
            val loginTextView = createRef()

            val idTextField = createRef()
            Text(
                text = stringResource(id = R.string.login),
                color = Teal900,
                fontSize = 25.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(loginTextView) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 32.dp)
                    }
            )

        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    HalIll_AndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            Login()
        }
    }
}