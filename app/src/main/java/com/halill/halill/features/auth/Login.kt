package com.halill.halill.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.halill.halill.R
import com.halill.halill.ui.theme.HalIll_AndroidTheme
import com.halill.halill.ui.theme.Teal200

@Composable
fun Login(darkTheme: Boolean = isSystemInDarkTheme()) {
    val backgroundColor = if(darkTheme) Color.Black else Teal200
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        val titleImage = createRef()
        val commentImage = createRef()
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