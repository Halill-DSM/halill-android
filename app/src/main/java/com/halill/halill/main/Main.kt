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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.main.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.halill.halill.R
import com.halill.halill.main.model.MainState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = viewModel()) {
    viewModel.loadUserInfoAndTodoList()
    val mainState = viewModel.mainState.observeAsState()

    when(mainState.value) {
        MainState.NotLoginState -> {
            navController.navigate("login")
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

    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = tabIndex
        ) {
            viewModel.showingPage.value = tabIndex
            tabData.forEachIndexed { index, text ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(text = text)
                })
            }
        }

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