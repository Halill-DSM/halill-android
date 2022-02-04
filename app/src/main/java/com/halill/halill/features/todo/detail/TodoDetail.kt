package com.halill.halill.features.todo.detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun TodoDetail(navController: NavController, id: Long, viewModel: TodoDetailViewModel = hiltViewModel()) {
    viewModel.getDetail(id)
}