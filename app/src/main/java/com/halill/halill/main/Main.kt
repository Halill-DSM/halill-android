package com.halill.halill.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.main.viewmodel.MainViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.halill.halill.R
import com.halill.halill.base.EventFlow
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.main.model.MainEvent
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

lateinit var scaffoldState: ScaffoldState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    scaffoldState = rememberScaffoldState()
    viewModel.loadTodoList()

    val tabData = listOf(
        stringResource(id = R.string.todo),
        stringResource(id = R.string.done)
    )

    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = tabData.size,
        infiniteLoop = false
    )

    Scaffold(scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar {

            }
        }) {
        Column {
            MainTab(pagerState = pagerState, tabData = tabData)

            MainPager(pagerState = pagerState, tabData = tabData)
        }
    }

    val mainEvent = viewModel.mainEvent
    handleMainEvent(navController = navController, event = mainEvent)
}

@Composable
private fun handleMainEvent(navController: NavController, event: EventFlow<MainEvent>) {
    event.observeWithLifecycle { mainEvent ->
        when (mainEvent) {
            is MainEvent.StartLogin -> {
                navController.navigate("login")
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainTab(
    pagerState: PagerState,
    tabData: List<String>,
    viewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        contentColor = Teal900
    ) {
        viewModel.showingPage.value = selectedTabIndex
        tabData.forEachIndexed { index, text ->
            Tab(selected = selectedTabIndex == index, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = {
                Text(text = text)
            })
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainPager(pagerState: PagerState, tabData: List<String>) {
    HorizontalPager(state = pagerState) { index ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (tabData[index] == stringResource(id = R.string.todo)) {

            } else {

            }
        }
    }
}