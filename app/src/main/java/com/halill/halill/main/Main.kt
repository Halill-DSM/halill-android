package com.halill.halill.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.R
import com.halill.halill.base.EventFlow
import com.halill.halill.base.observeWithLifecycle
import com.halill.halill.ui.theme.Teal500
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import com.halill.halill.util.toShowDeadlineText
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


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
) =
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        backgroundColor = Teal700
    ) {
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.List.route,
                    navController
                )
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
                contentDescription = null
            )
        }

        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Calendar.route,
                    navController
                )
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
                contentDescription = null
            )
        }

        Box(modifier = Modifier.weight(1f),)
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun List(viewModel: MainViewModel) {
    val mainState = viewModel.state.collectAsState().value
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
        MainTab(pagerState = pagerState, tabData = tabData)

        MainPager(
            mainState = mainState,
            pagerState = pagerState,
            tabData = tabData,
            onItemClick = { id -> viewModel.startDetailTodo(id) },
            onDoneClick = { id -> viewModel.doneTodo(id) },
            onDeleteClick = { id -> viewModel.deleteTodo(id) }
        )
    }
}

@Composable
fun Calendar() {

}

@ExperimentalPagerApi
@Composable
fun MainTab(
    pagerState: PagerState,
    tabData: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        contentColor = Teal900
    ) {
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
    mainState: MainState,
    pagerState: PagerState,
    tabData: List<String>,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    HorizontalPager(state = pagerState) { index ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                mainState.isLoading -> LoadingText()
                checkBothListIsEmpty(mainState) -> BothEmptySoRequireTodoText()
                else -> ShowList(
                    state = mainState,
                    tabTitle = tabData[index],
                    onItemClick,
                    onDoneClick,
                    onDeleteClick
                )
            }

        }
    }
}

private fun checkBothListIsEmpty(state: MainState): Boolean =
    state.doneList.isEmpty() && state.todoList.isEmpty()

@Composable
fun BothEmptySoRequireTodoText() {
    val requireTodo = stringResource(id = R.string.require_todo_comment)
    Text(text = requireTodo)
}

@Composable
fun LoadingText() {
    val loadingComment = stringResource(id = R.string.loading_comment)
    Text(text = loadingComment)
}

@Composable
fun ShowList(
    state: MainState,
    tabTitle: String,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    val todoList = state.todoList
    val doneList = state.doneList
    if (tabTitle == stringResource(id = R.string.todo)) {
        if (todoList.isEmpty()) {
            EmptyTodoListText()
        } else {
            TodoList(todoList, onItemClick, onDoneClick)
        }
    } else {
        if (doneList.isEmpty()) {
            EmptyDoneListText()
        } else {
            DoneList(doneList, onItemClick, onDeleteClick)
        }
    }
}

@Composable
fun TodoList(todoList: List<TodoEntity>, onItemClick: (Long) -> Unit, onDoneClick: (Long) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp, 4.dp)
    ) {
        items(todoList) {
            TodoItem(todo = it, onItemClick = onItemClick, onDoneClick = onDoneClick)
        }
    }

}

@Composable
fun TodoItem(todo: TodoEntity, onItemClick: (Long) -> Unit, onDoneClick: (Long) -> Unit) {
    Box(modifier = Modifier
        .clickable(enabled = true, role = Role.Tab) {
            onItemClick(todo.id)
        }) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(PaddingValues(4.dp, 0.dp))
        ) {
            TitleText(title = todo.title)
            ContentText(content = todo.content)
            DeadlineText(deadline = todo.deadline)
            Divider(
                modifier = Modifier.padding(PaddingValues(0.dp, 8.dp)),
                color = Teal500,
                thickness = 1.dp
            )
        }
        DoneButton(modifier = Modifier
            .align(Alignment.TopEnd)
            .size(37.dp)
            .clickable(enabled = true, role = Role.Button) {
                onDoneClick(todo.id)
            })

    }
}

@Composable
fun DoneButton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_baseline_done_24),
            contentDescription = "doneIcon"
        )
        val doneText = stringResource(id = R.string.done)
        Text(text = doneText, color = colorResource(id = R.color.green), fontSize = 12.sp)
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        modifier = Modifier.padding(0.dp, 0.dp, 35.dp, 0.dp),
        text = title,
        fontSize = 18.sp,
        fontWeight = Bold,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ContentText(content: String) {
    Text(
        modifier = Modifier.padding(0.dp, 0.dp, 35.dp, 0.dp),
        text = content,
        fontSize = 14.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun DeadlineText(deadline: LocalDateTime, done: Boolean = false) {
    val deadlineText = deadline.toShowDeadlineText()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!done) {
            val color = calculateRemainTimeToColor(deadline)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
                    .padding(2.dp)
            )
        }
        Text(
            text = deadlineText,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(2.dp)
        )
    }
}

private fun calculateRemainTimeToColor(deadline: LocalDateTime): Color {
    val currentTime = LocalDateTime.now()
    val differenceTimeBetweenDeadline = ChronoUnit.DAYS.between(currentTime, deadline)
    return when {
        differenceTimeBetweenDeadline < 1 -> Color.Red
        differenceTimeBetweenDeadline > 3 -> Teal700
        else -> Color.Yellow
    }
}

@Composable
fun EmptyTodoListText() {
    val emptyComment = stringResource(id = R.string.empty_todo_list)
    Text(text = emptyComment)
}


@Composable
fun DoneList(
    doneList: List<TodoEntity>,
    onItemClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp, 4.dp)
    ) {
        items(doneList) {
            DoneItem(it, onItemClick, onDeleteClick)
        }
    }

}

@Composable
fun DoneItem(done: TodoEntity, onItemClick: (Long) -> Unit, onDeleteClick: (Long) -> Unit) {
    Box(modifier = Modifier
        .clickable(enabled = true, role = Role.Tab) {
            onItemClick(done.id)
        }) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(PaddingValues(4.dp, 0.dp))
        ) {
            TitleText(title = done.title)
            ContentText(content = done.content)
            DeadlineText(deadline = done.deadline, done = true)
            Divider(
                modifier = Modifier.padding(PaddingValues(0.dp, 8.dp)),
                color = Teal500,
                thickness = 1.dp
            )
        }
        DeleteButton(modifier = Modifier
            .align(Alignment.TopEnd)
            .size(37.dp)
            .clickable(enabled = true, role = Role.Button) {
                onDeleteClick(done.id)
            })
    }
}

@Composable
fun DeleteButton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_baseline_delete_24),
            contentDescription = "delete"
        )
        val doneText = stringResource(id = R.string.delete)
        Text(text = doneText, color = colorResource(id = R.color.red), fontSize = 12.sp)
    }
}

@Composable
fun EmptyDoneListText() {
    val emptyComment = stringResource(id = R.string.empty_done_list)
    Text(text = emptyComment)
    ResultText(comment = "")
}

@Composable
fun ResultText(comment: String) {
    Text(text = comment)
}