package com.halill.halill.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.main.viewmodel.MainViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.halill.halill.R
import com.halill.halill.main.model.MainState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val mainState = viewModel.mainState.observeAsState()
    viewModel.loadTodoList()
    when(mainState.value) {
        MainState.NotLoginState -> {
            navController.navigate("login") {
                launchSingleTop = true
            }
        }
    }
    val tabData = listOf(
        stringResource(id = R.string.todo),
        stringResource(id = R.string.done)
    )

    val pagerState = rememberPagerState(
        pageCount = tabData.size,
        initialOffscreenLimit = tabData.size,
        infiniteLoop = false
    )

    Column {

        MainTab(pagerState = pagerState, selectedTabIndex = pagerState.currentPage, tabData = tabData)

        HorizontalPager(state = pagerState) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(tabData[index] == stringResource(id = R.string.todo)) {

                } else {

                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainTab(pagerState: PagerState, selectedTabIndex: Int, tabData: List<String>, viewModel: MainViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = selectedTabIndex
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