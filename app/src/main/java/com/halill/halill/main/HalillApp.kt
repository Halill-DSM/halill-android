package com.halill.halill.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.halill.halill.features.auth.login.Login
import com.halill.halill.features.auth.register.Register
import com.halill.halill.features.todo.detail.TodoDetail
import com.halill.halill.features.todo.WriteTodo
import com.halill.halill.features.todo.edit.EditTodo
import com.halill.halill.ui.theme.HalIll_AndroidTheme
import com.halill.halill.ui.theme.Teal900

@Composable
fun HalIllApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    if (isSystemInDarkTheme()) {
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Teal900
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { Main(navController) }
        composable("login") { Login(navController) }
        composable("register") { Register(navController) }
        composable("writeTodo") { WriteTodo(navController) }
        composable(
            route = "todoDetail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) {
            val todoId = it.arguments!!.getLong("todoId")
            TodoDetail(navController, todoId)
        }
        composable(
            route = "editTodo/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) {
            val todoId = it.arguments!!.getLong("todoId")
            EditTodo(navController = navController, todoId)
        }
    }
}


@Preview
@Composable
fun PreViewMain() {
    HalIll_AndroidTheme {
        Surface(color = MaterialTheme.colors.background) {
            HalIllApp()
        }
    }
}