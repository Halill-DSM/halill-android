package com.halill.halill.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.halill.halill.features.auth.Login
import com.halill.halill.main.viewmodel.MainViewModel
import com.halill.halill.ui.theme.Halill_AndroidTheme

@Composable
fun HalIllApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { Main(navController) }
        composable("login") { Login()}
        composable("writeTodo") {}
    }
}


@Preview
@Composable
fun PreViewMain() {
    Halill_AndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            HalIllApp()
        }
    }
}