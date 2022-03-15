package com.halill.halill.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.R
import com.halill.halill.base.EventFlow
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.ui.theme.Teal700


lateinit var scaffoldState: ScaffoldState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    scaffoldState = rememberScaffoldState()
    viewModel.loadTodoList()

    val navHostController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("writeTodo") },
                Modifier.padding(0.dp)
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
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
            composable(BottomNavigationItem.List.route) { List(viewModel) }
            composable(BottomNavigationItem.Calendar.route) { Calendar() }
            composable(BottomNavigationItem.MyPage.route) { MyPage() }
        }
    }

    val mainEvent = viewModel.mainViewEffect
    handleMainViewEffect(navController = navController, uiEvent = mainEvent)
}

@Composable
private fun handleMainViewEffect(navController: NavController, uiEvent: EventFlow<MainViewEffect>) {
    val deleteComment = stringResource(id = R.string.delete_comment)
    uiEvent.observeWithLifecycle { mainEvent ->
        when (mainEvent) {
            is MainViewEffect.DoneDeleteTodo -> {
                scaffoldState.snackbarHostState.showSnackbar(deleteComment)
            }

            is MainViewEffect.StartTodoDetail -> {
                navController.navigate("todoDetail/${mainEvent.id}")
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    var bottomTabSelectedItem: BottomNavigationItem by remember {
        mutableStateOf(BottomNavigationItem.List)
    }

    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        backgroundColor = Teal700
    ) {
        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.List.route,
                    navController
                )
                bottomTabSelectedItem = BottomNavigationItem.List
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem is BottomNavigationItem.List
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Calendar.route,
                    navController
                )
                bottomTabSelectedItem = BottomNavigationItem.Calendar
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem is BottomNavigationItem.Calendar
        )

        BottomNavigationItem(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.MyPage.route,
                    navController
                )
                bottomTabSelectedItem = BottomNavigationItem.MyPage
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem is BottomNavigationItem.MyPage
        )

        Box(modifier = Modifier.weight(1f))
    }
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