package com.halill.halill.features.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun MyPage(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    viewModel.fetchUserInfo()

    viewModel.myPageViewEffect.observeWithLifecycle {
        when (it) {
            is MyPageViewEffect.StartLogin -> {
                navController.navigate("login")
            }
        }
    }
    val myPageState = viewModel.state.collectAsState().value
    MyPageContent(
        myPageState,
        doOnLogoutClick = {
            coroutineScope.launch {
                viewModel.logout()
            }
        },
        doOnNameTextClick = {
            viewModel.showSaveNameDialog()
        },
        doOnDoneSaveName = { name ->
            viewModel.run {
                saveUserName(name)
                dismissSaveNameDialog()
                fetchUserInfo()
            }
        }
    )
}

@Composable
fun MyPageContent(
    myPageState: MyPageState,
    doOnLogoutClick: () -> Unit,
    doOnNameTextClick: () -> Unit,
    doOnDoneSaveName: (String) -> Unit
) {
    Row(horizontalArrangement = SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        val userName = myPageState.name.ifBlank { "User" }
        NameText(name = userName, doOnEditNameClick = doOnNameTextClick)

        LogoutButton(doOnClick = {
            doOnLogoutClick()
        })
    }
    if (myPageState.showSaveNameDialog) {
        SaveUserNameDialog(userName = myPageState.name, doOnDone = doOnDoneSaveName)
    }
}

@Composable
fun SaveUserNameDialog(userName: String, doOnDone: (String) -> Unit) {
    val name = remember {
        mutableStateOf(userName)
    }
    Dialog(onDismissRequest = { doOnDone(name.value) }) {
        Column {
            TextField(value = name.value, onValueChange = { name.value = it })
        }
    }
}

@Composable
fun NameText(name: String, doOnEditNameClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp)
    ) {
        Row(modifier = Modifier.clickable {
            doOnEditNameClick()
        }) {
            Text(
                text = name,
                fontSize = 20.sp,
                color = Teal900
            )
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                contentDescription = "editNameIcon"
            )
        }

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