package com.halill.halill.features.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.features.auth.login.LoginLayoutViews
import com.halill.halill.features.todo.model.WriteTodoState
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

@Composable
fun WriteTodo(navController: NavController, viewModel: WriteTodoViewModel = hiltViewModel()) {
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.write_todo))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                backgroundColor = Teal700,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleTextField()
                ContentTextField()
                DeadLineView()
                WriteTodoButton()
                val writeTodoState = viewModel.writeTodoState.collectAsState().value
                if(writeTodoState is WriteTodoState.SelectDateState) {
                    SelectDateDialog()
                }
            }
        })

}

@Composable
fun TitleTextField(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val title = viewModel.title.collectAsState()
    val titleLabel = stringResource(id = R.string.write_title)
    OutlinedTextField(
        value = title.value,
        label = { Text(text = titleLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        onValueChange = {
            viewModel.setTitle(it)
        }
    )
}

@Composable
fun ContentTextField(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val content = viewModel.content.collectAsState()
    val contentLabel = stringResource(id = R.string.write_content)
    OutlinedTextField(
        value = content.value,
        label = { Text(text = contentLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp, minWidth = 100.dp)
            .padding(10.dp),
        onValueChange = {
            viewModel.setContent(it)
        }
    )
}

@Composable
fun DeadLineView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.End
    ) {
        val deadLineText = stringResource(id = R.string.dead_line)
        Text(modifier = Modifier.weight(2f), text = deadLineText)
        Column(horizontalAlignment = Alignment.End) {
            DeadLineDateView()
            DeadLineTimeView()
        }
    }
}

@Composable
fun DeadLineDateView(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadLine = viewModel.deadLine.value
    val year = "${deadLine.year}년"
    val month = "${deadLine.monthValue}월"
    val date = "${deadLine.dayOfMonth}일"
    val dateText = "$year $month $date"
    Text(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .clickable(enabled = true, role = Role.Button) {
                viewModel.setSelectDateState()
            },
        text = dateText,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )

}

@Composable
fun DeadLineTimeView(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadLine = viewModel.deadLine.value
    val hour = "${deadLine.hour}시"
    val minute = "${deadLine.minute}분"
    val timeText = "$hour $minute"
    Text(
        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 10.dp),
        text = timeText,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )
}

@Composable
fun WriteTodoButton(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val writeTodoState = viewModel.writeTodoState.collectAsState()
    val emptyComment = stringResource(id = R.string.login_empty_comment)
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            if (writeTodoState.value is WriteTodoState.DoneInputState) {
                viewModel.writeTodo()
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        emptyComment,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (writeTodoState.value is WriteTodoState.DoneInputState) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .layoutId(LoginLayoutViews.LoginButton)
            .width(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Text(text = stringResource(id = R.string.write_todo))
    }
}

@Composable
fun SelectDateDialog(viewModel: WriteTodoViewModel = hiltViewModel()) {
    Dialog(onDismissRequest = { viewModel.checkDoneInput() }) {
        Surface(
            modifier = Modifier
                .wrapContentSize(),
            color = Color.White
        ) {
            DateDialogContent()
        }
    }
}

@Composable
fun DateDialogContent() {

}