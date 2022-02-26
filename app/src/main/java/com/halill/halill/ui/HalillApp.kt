package com.halill.halill.ui

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
import com.halill.halill.features.todo.detail.TodoDetail
import com.halill.halill.features.todo.write.WriteTodo
import com.halill.halill.main.Main
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
        composable(
            route = "writeTodo?todoId={todoId}",
            arguments = listOf(navArgument("todoId") {
                defaultValue = -1L
                type = NavType.LongType
            })
        ) {
            WriteTodo(navController, it.arguments!!.getLong("todoId"))
        }
        composable(
            route = "todoDetail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) {
            val todoId = it.arguments!!.getLong("todoId")
            TodoDetail(navController, todoId)
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