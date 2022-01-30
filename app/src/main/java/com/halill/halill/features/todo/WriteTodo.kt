package com.halill.halill.features.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal700

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