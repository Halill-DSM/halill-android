package com.halill.halill2

import android.app.Application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.halill.halill2.features.auth.login.Login
import com.halill.halill2.features.auth.register.Register
import com.halill.halill2.features.detail.TodoDetail
import com.halill.halill2.features.write.WriteTodo
import com.halill.halill2.main.Main
import com.halill.halill2.theme.Teal900
import dagger.hilt.android.HiltAndroidApp

@Composable
fun HalIllApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val scaffoldState = rememberScaffoldState()

    val textFieldBackgroundColor = compositionLocalOf { Color.White }

    if (isSystemInDarkTheme()) {
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false
        )
        textFieldBackgroundColor.provides(Color.Gray)
    } else {
        systemUiController.setSystemBarsColor(
            color = Teal900
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
        textFieldBackgroundColor.provides(Color.White)
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

        composable(
            route = "login"
        ) {
            Login(navController = navController, scaffoldState = scaffoldState)
        }

        composable(
            route = "register"
        ) {
            Register(scaffoldState = scaffoldState, navController = navController)
        }
    }
}

@HiltAndroidApp
class HalIllApplication: Application()