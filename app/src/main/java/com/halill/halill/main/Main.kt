package com.halill.halill.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.halill.main.viewmodel.MainViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.R
import com.halill.halill.base.EventFlow
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.main.model.MainEvent
import com.halill.halill.main.model.MainState
import com.halill.halill.ui.theme.Teal500
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch

lateinit var scaffoldState: ScaffoldState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    scaffoldState = rememberScaffoldState()
    viewModel.run {
        checkLogin()
        loadUserInfo()
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

    Scaffold(scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("writeTodo") }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
                    contentDescription = "write todo"
                )
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                val userName =
                    when (val mainState = viewModel.mainState.collectAsState().value) {
                        is MainState.ShowTodoListState -> mainState.userEntity.name
                        is MainState.EmptyListState -> mainState.userEntity.name
                        is MainState.LoadingState -> {
                            val loadingText = stringResource(id = R.string.loading_comment)
                            loadingText
                        }
                    }
                Text(text = userName)
            }
        }) {
        Column {
            MainTab(pagerState = pagerState, tabData = tabData)

            MainPager(pagerState = pagerState, tabData = tabData)
        }
    }

    val mainEvent = viewModel.mainEvent
    HandleMainEvent(navController = navController, event = mainEvent)
}

@Composable
private fun HandleMainEvent(navController: NavController, event: EventFlow<MainEvent>) {
    event.observeWithLifecycle { mainEvent ->
        when (mainEvent) {
            is MainEvent.StartLogin -> {
                navController.navigate("login") {
                    launchSingleTop = true
                }
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
fun MainPager(
    pagerState: PagerState,
    tabData: List<String>,
    viewModel: MainViewModel = hiltViewModel()
) {
    HorizontalPager(state = pagerState) { index ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.mainState.collectAsState().value) {
                is MainState.ShowTodoListState -> {
                    ShowList(state = state, tabTitle = tabData[index])
                }
                is MainState.LoadingState -> {
                    LoadingText()
                }
                is MainState.EmptyListState -> {
                    EmptyTodoListText()
                }
            }
        }
    }
}

@Composable
fun LoadingText() {
    val loadingComment = stringResource(id = R.string.loading_comment)
    Text(text = loadingComment)
}

@Composable
fun ShowList(state: MainState.ShowTodoListState, tabTitle: String) {
    val todoList = state.todoList
    val doneList = state.doneList
    if (tabTitle == stringResource(id = R.string.todo)) {
        TodoList(todoList)
    } else {
        DoneList(doneList)
    }
}

@Composable
fun TodoList(todoList: List<TodoEntity>) {
    if (todoList.isEmpty()) {
        EmptyTodoListText()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp, 4.dp)
        ) {
            items(todoList) {
                TodoItem(todo = it)
            }
        }
    }
}

@Composable
fun TodoItem(todo: TodoEntity) {
    Box {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(PaddingValues(4.dp, 0.dp))
        ) {
            TitleText(title = todo.title)
            ContentText(content = todo.content)
            Divider(
                modifier = Modifier.padding(PaddingValues(0.dp, 8.dp)),
                color = Teal500,
                thickness = 1.dp
            )
        }
        Image(
            modifier = Modifier
                .size(37.dp)
                .align(Alignment.CenterEnd)
                .clickable(enabled = true, role = Role.Button) {

                },
            painter = painterResource(R.drawable.ic_baseline_done_24),
            contentDescription = "doneIcon"
        )
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        modifier = Modifier.padding(0.dp, 0.dp, 40.dp, 0.dp),
        text = title,
        fontSize = 18.sp,
        fontWeight = Bold,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ContentText(content: String) {
    Text(
        modifier = Modifier.padding(0.dp, 0.dp, 40.dp, 0.dp),
        text = content,
        fontSize = 14.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun EmptyTodoListText() {
    val emptyComment = stringResource(id = R.string.empty_todo_list)
    Text(text = emptyComment)
}

@Composable
fun DoneList(doneList: List<TodoEntity>) {
    if (doneList.isEmpty()) {
        EmptyDoneListText()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp, 4.dp)
        ) {
            items(doneList) {
                TodoItem(todo = it)
            }
        }
    }
}

@Composable
fun DoneItem(done: TodoEntity) {

}

@Composable
fun EmptyDoneListText() {
    val emptyComment = stringResource(id = R.string.empty_done_list)
    Text(text = emptyComment)
}
