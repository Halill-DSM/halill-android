package com.halill.halill.features.todo.detail

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    LaunchedEffect(Unit){
        viewModel.getDetail(id)
    }
    val state = viewModel.todoDetailState.collectAsState().value
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    if (state is TodoDetailState.MainState) {
                        Text(text = state.todo.title)
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