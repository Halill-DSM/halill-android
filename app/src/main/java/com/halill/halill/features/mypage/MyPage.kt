package com.halill.halill.features.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.ui.theme.Black
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun MyPage(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val myPageState = viewModel.state.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.run {
            fetchUserInfo()
            fetchCurrentCount()
        }
    }

    LaunchedEffect(myPageState) {
        viewModel.fetchAllTimeCount()
    }

    val scaffoldState = rememberScaffoldState()

    val saveNameFailedComment = stringResource(id = R.string.save_name_failed)
    viewModel.myPageViewEffect.observeWithLifecycle {
        when (it) {
            is MyPageViewEffect.StartLogin -> {
                navController.navigate("login")
            }

            is MyPageViewEffect.SaveNameFailed -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    saveNameFailedComment
                )
            }
        }
    }
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
    Column {
        Row(
            horizontalArrangement = SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val userName = myPageState.name.ifBlank { "User" }
            NameText(name = userName, doOnEditNameClick = doOnNameTextClick)

            LogoutButton(doOnClick = {
                doOnLogoutClick()
            })
        }

        MyPageCountLayout(myPageState)
    }

    if (myPageState.showSaveNameDialog) {
        SaveUserNameDialog(userName = myPageState.name, doOnDone = doOnDoneSaveName)
    }
}

@Composable
fun MyPageCountLayout(myPageState: MyPageState) {
    val currentTodoTitle = stringResource(id = R.string.current_todo_count)
    val currentDoneTitle = stringResource(id = R.string.current_done_count)
    val allTimeCountTitle = stringResource(id = R.string.all_count)
    val allTimeDoneTitle = stringResource(id = R.string.all_time_done_todo_count)

    Column(
        verticalArrangement = SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(30.dp, 60.dp, 30.dp, 122.dp)
    ) {
        Divider(color = Teal700, thickness = 1.dp)
        MyPageCountContent(title = currentTodoTitle, count = myPageState.currentTodoCount)
        Divider(color = Teal700, thickness = 1.dp)
        MyPageCountContent(title = currentDoneTitle, count = myPageState.currentDoneCount)
        Divider(color = Teal700, thickness = 1.dp)
        MyPageCountContent(
            title = allTimeDoneTitle,
            count = myPageState.allTimeDoneTodoCount,
            countColor = Teal900
        )
        Divider(color = Teal700, thickness = 1.dp)
        MyPageCountContent(
            title = allTimeCountTitle,
            count = myPageState.allCount,
            countColor = Teal900
        )
        Divider(color = Teal700, thickness = 1.dp)
    }
}

@Composable
fun SaveUserNameDialog(userName: String, doOnDone: (String) -> Unit) {
    val name = remember {
        mutableStateOf(userName)
    }
    Dialog(onDismissRequest = { doOnDone(name.value) }) {
        Column(
            Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
        ) {
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                modifier = Modifier
                    .padding(0.dp, 30.dp)
                    .fillMaxWidth()
            )
            Button(onClick = { doOnDone(name.value) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.done_input))
            }
        }
    }
}

@Composable
fun NameText(name: String, doOnEditNameClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Bottom
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

@Composable
fun MyPageCountContent(title: String, count: Int, countColor: Color = Black) {
    Row(horizontalArrangement = SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(text = title)
        val countText = "$count ${stringResource(id = R.string.count)}"
        Text(text = countText, color = countColor, fontSize = 20.sp)
    }
}