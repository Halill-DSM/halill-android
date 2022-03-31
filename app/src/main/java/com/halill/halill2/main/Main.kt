package com.halill.halill2.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill2.R
import com.halill.halill2.base.observeWithLifecycle
import com.halill.halill2.features.calendar.Calendar
import com.halill.halill2.features.list.ListPage
import com.halill.halill2.features.mypage.MyPage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val navHostController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.checkLogin()
    }

    viewModel.mainViewEffect.observeWithLifecycle {
        when (it) {

            is MainViewEffect.StartLogin -> {
                startLogin(navController = navController)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("writeTodo") },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_edit_white),
                    contentDescription = "write todo"
                )
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = { BottomNavBar(navController = navHostController) }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.List.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationItem.List.route) { ListPage(navController = navController) }
            composable(BottomNavigationItem.Calendar.route) { Calendar(navController) }
            composable(BottomNavigationItem.MyPage.route) { MyPage() }
        }
    }
}

private fun startLogin(navController: NavController) {
    navController.navigate("login") {
        launchSingleTop = true
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController
) =
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White
    ) {
        val bottomTabSelectedItem = rememberSaveable {
            mutableStateOf(BottomNavigationItem.List.route)
        }
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.List.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.List.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.List.route
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Calendar.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.Calendar.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.Calendar.route
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.MyPage.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.MyPage.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.MyPage.route
        )

        Box(modifier = Modifier.weight(1f))
    }


private fun navigateBottomNavigation(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}