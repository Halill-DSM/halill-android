package com.halill.halill.features.todo.detail

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.main.DeadlineText
import com.halill.halill.main.DeleteButton
import com.halill.halill.main.DoneButton
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.util.toRemainTimeText
import kotlinx.coroutines.launch

@Composable
fun TodoDetail(
    navController: NavController,
    id: Long,
    viewModel: TodoDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getDetail(id)
    }
    val state = viewModel.todoDetailState.collectAsState().value
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    if (state is TodoDetailState.MainState) {
                        var title = state.todo.title
                        if (state.todo.isCompleted) {
                            title += stringResource(id = R.string.done_comment)
                        }
                        Text(text = title)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                actions = {
                    val scope = rememberCoroutineScope()

                    if (state is TodoDetailState.MainState) {
                        IconButton(onClick = {
                            navController.navigate("writeTodo?todoId=${state.todo.id}")
                        }) {
                            EditButton()
                        }
                        if (state.todo.isCompleted) {
                            IconButton(onClick = {
                                scope.launch {
                                    viewModel.deleteTodo(id)
                                }
                                navController.popBackStack()
                            }) {
                                DeleteButton()
                            }
                        } else {
                            IconButton(onClick = {
                                viewModel.doneTodo(state.todo.id)
                            }) {
                                DoneButton()
                            }
                        }
                    }

                },
                backgroundColor = Color.White,
                contentColor = Teal700,
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
                if (state is TodoDetailState.MainState) {
                    Text(
                        text = state.todo.content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp)
                    )
                    DeadlineText(deadline = state.todo.deadline)
                    Text(text = state.todo.deadline.toRemainTimeText())
                }

            }
        })
}

@Composable
fun EditButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_baseline_edit_24),
            contentDescription = "editTodo"
        )
        val doneText = stringResource(id = R.string.edit)
        Text(text = doneText, color = Color.Gray, fontSize = 12.sp)
    }
}