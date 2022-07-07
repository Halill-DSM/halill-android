package com.halill.halill2.features.mypage

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
import com.halill.halill2.R
import com.halill.halill2.base.observeWithLifecycle
import com.halill.halill2.theme.Teal900
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@Composable
fun MyPage(
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

    LaunchedEffect(myPageState.currentTodoCount, myPageState.currentDoneCount) {
        viewModel.fetchAllTimeCount()
    }

    val scaffoldState = rememberScaffoldState()

    val saveNameFailedComment = stringResource(id = R.string.save_name_failed)
    viewModel.myPageViewEffect.observeWithLifecycle {
        when (it) {

            is MyPageViewEffect.SaveNameFailed -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    saveNameFailedComment
                )
            }
        }
    }
    MyPageContent(
        myPageState,
        doOnLogoutButtonClick = {
            viewModel.showLogoutDialog()
        },
        doOnLogoutClick = {
            coroutineScope.launch {
                viewModel.dismissLogoutDialog()
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
        },
        doOnLogoutDismiss = {
            viewModel.dismissLogoutDialog()
        }
    )
}

@Composable
fun MyPageContent(
    myPageState: MyPageState,
    doOnLogoutButtonClick: () -> Unit,
    doOnLogoutClick: () -> Unit,
    doOnLogoutDismiss: () -> Unit,
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

            LogoutButton(doOnClick = doOnLogoutButtonClick)
        }

        MyPageCountLayout(myPageState)
    }

    if (myPageState.showSaveNameDialog) {
        SaveUserNameDialog(userName = myPageState.name, doOnDone = doOnDoneSaveName)
    }

    if (myPageState.showLogoutDialog) {
        LogoutDialog(doOnLogoutClick = doOnLogoutClick, doOnDismiss = doOnLogoutDismiss)
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
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)

        MyPageCountContent(
            title = currentTodoTitle,
            count = myPageState.currentTodoCount.toString()
        )
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)

        MyPageCountContent(
            title = currentDoneTitle,
            count = myPageState.currentDoneCount.toString()
        )
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)

        val allTimeDoneText =
            if (myPageState.isLoading) "..." else myPageState.allTimeDoneTodoCount.toString()
        MyPageCountContent(
            title = allTimeDoneTitle,
            count = allTimeDoneText,
            countColor = Teal900
        )
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)

        val allTimeTodoText = if (myPageState.isLoading) "..." else myPageState.allCount.toString()
        MyPageCountContent(
            title = allTimeCountTitle,
            count = allTimeTodoText,
            countColor = Teal900
        )
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)
    }
}

@Composable
fun SaveUserNameDialog(userName: String, doOnDone: (String) -> Unit) {
    val name = remember {
        mutableStateOf(userName)
    }
    Dialog(onDismissRequest = { doOnDone(name.value) }) {
        Column(
            horizontalAlignment = Alignment.End, modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            val limitLength = 10
            val currentLength = name.value.length
            Text(
                text = "$currentLength / $limitLength",
                color = Teal900,
                modifier = Modifier.padding(10.dp)
            )
            TextField(
                value = name.value,
                onValueChange = {
                    if (it.length <= limitLength) {
                        name.value = it
                    }
                },
                modifier = Modifier
                    .padding(0.dp, 15.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = { doOnDone(name.value) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.done_input))
            }
        }
    }
}

@Composable
fun LogoutDialog(doOnLogoutClick: () -> Unit, doOnDismiss: () -> Unit) {
    Dialog(onDismissRequest = doOnDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = stringResource(id = R.string.ask_logout),
                modifier = Modifier
                    .padding(0.dp, 30.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.no),
                    modifier = Modifier
                        .clickable { doOnDismiss() }
                        .padding(20.dp))
                Text(
                    text = stringResource(id = R.string.logout),
                    color = Color.Red,
                    modifier = Modifier
                        .clickable { doOnLogoutClick() }
                        .padding(20.dp))
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
fun MyPageCountContent(title: String, count: String, countColor: Color = Color.Unspecified) {
    Row(horizontalArrangement = SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(text = title)
        val countText = "$count ${stringResource(id = R.string.count)}"
        Text(text = countText, color = countColor, fontSize = 20.sp)
    }
}