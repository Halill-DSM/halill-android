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
import com.halill.halill.features.list.ListPage
import com.halill.halill.ui.theme.Teal700
import java.lang.Exception

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()

    val navHostController = rememberNavController()

    val floatingIcon = rememberVectorPainter(image = Icons.Filled.Add)

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("writeTodo") },
                Modifier.padding(0.dp)
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
            composable(BottomNavigationItem.MyPage.route) { MyPage() }
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
                if (bottomTabSelectedItem is BottomNavigationItem.Calendar) {
                    return@BottomNavigationItem
                }
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
                if (bottomTabSelectedItem is BottomNavigationItem.MyPage) {
                    return@BottomNavigationItem
                }
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
    try {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    } catch (e: Exception) {

    }

}