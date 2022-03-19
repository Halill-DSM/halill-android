package com.halill.halill.features.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.halill.R
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun MyPage(
    navController: NavController,
    userEntity: UserEntity,
    viewModel: MyPageViewModel = hiltViewModel()
) {

    LaunchedEffect(userEntity) {
        viewModel.setUser(userEntity)
    }

    viewModel.myPageViewEffect.observeWithLifecycle {
        when (it) {
            is MyPageViewEffect.StartLogin -> {
                navController.navigate("login")
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val myPageState = viewModel.state.collectAsState().value
    MyPageContent(myPageState, doOnLogoutClick = {
        coroutineScope.launch {
            viewModel.logout()
        }
    })
}

@Composable
fun MyPageContent(myPageState: MyPageState, doOnLogoutClick: () -> Unit) {
    Row(horizontalArrangement = SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        NameText(name = myPageState.name)
        LogoutButton(doOnClick = {
            doOnLogoutClick()
        })
    }
}

@Composable
fun NameText(name: String) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp)
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            color = Teal900
        )
        Text(
            text = stringResource(id = R.string.mypage_comment),
            modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}

@Composable
fun LogoutButton(doOnClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_logout),
        contentDescription = "Logout",
        modifier = Modifier
            .size(44.dp)
            .clickable {
                doOnClick()
            }
            .padding(10.dp)
    )
}