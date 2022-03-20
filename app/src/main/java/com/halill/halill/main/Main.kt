package com.halill.halill.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.R
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.features.calendar.Calendar
import com.halill.halill.features.list.ListPage
import com.halill.halill.features.mypage.MyPage
import com.halill.halill.ui.theme.Teal700

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val navHostController = rememberNavController()

    val floatingIcon = rememberVectorPainter(image = Icons.Filled.Add)

    viewModel.fetchUserInfo()

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
                onClick = { navController.navigate("writeTodo") }
            ) {
                Icon(
                    painter = floatingIcon,
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
            composable(BottomNavigationItem.Calendar.route) { Calendar() }
            composable(BottomNavigationItem.MyPage.route) { MyPage(navController) }
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
        backgroundColor = Teal700
    ) {
        val bottomTabSelectedItem = rememberSaveable {
            mutableStateOf<String>(BottomNavigationItem.List.route)
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