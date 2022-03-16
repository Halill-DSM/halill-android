package com.halill.halill.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.R
import com.halill.halill.ui.theme.Teal500
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.util.toShowDeadlineText
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalPagerApi::class)
@Composable
fun List(viewModel: MainViewModel) {
    val mainState = viewModel.state.collectAsState().value

    Column(horizontalAlignment = Alignment.End) {
        SwitchContentDoneOrTodoText(mainState = mainState) {
            viewModel.switchTodoOrDone()
        }

        MainPager(
            mainState = mainState,
            onItemClick = { id -> viewModel.startDetailTodo(id) },
            onDoneClick = { id -> viewModel.doneTodo(id) },
            onDeleteClick = { id -> viewModel.deleteTodo(id) }
        )
    }
}

@Composable
fun SwitchContentDoneOrTodoText(mainState: MainState, doOnClick: () -> Unit) {
    val text = if (mainState.showDoneList) "할일보기" else "완료한 할일 보기"
    val icon = if (mainState.showDoneList) Icons.Filled.RadioButtonUnchecked else Icons.Filled.Check
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(150.dp)
            .clickable { doOnClick() }) {
        Image(icon, contentDescription = "swipe")
        Text(
            text = text, fontSize = 15.sp,
            modifier = Modifier
                .padding(10.dp),
        )
    }

}

@ExperimentalPagerApi
@Composable
fun MainPager(
    mainState: MainState,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
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
                onItemClick,
                onDoneClick,
                onDeleteClick
            )
        }

    }
}

private fun checkBothListIsEmpty(state: MainState): Boolean =
    state.doneList.isEmpty() && state.todoList.isEmpty()

@Composable
fun ShowList(
    state: MainState,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    val todoList = state.todoList
    val doneList = state.doneList
    if (state.showDoneList) {
        ShowDoneList(doneList = doneList, onItemClick = onItemClick, onDeleteClick = onDeleteClick)
    } else {
        ShowTodoList(todoList, onItemClick, onDoneClick)
    }
}

@Composable
fun ShowDoneList(
    doneList: List<TodoEntity>,
    onItemClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    if (doneList.isEmpty()) {
        EmptyDoneListText()
    } else {
        DoneList(doneList, onItemClick, onDeleteClick)
    }
}

@Composable
fun ShowTodoList(
    todoList: List<TodoEntity>,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit
) {
    if (todoList.isEmpty()) {
        EmptyTodoListText()
    } else {
        TodoList(todoList, onItemClick, onDoneClick)
    }
}

@Composable
fun TodoList(
    todoList: List<TodoEntity>,
    onItemClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit
) {
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
            DoneTitleText(title = done.title)
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
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun DoneTitleText(title: String) {
    val text = title + stringResource(id = R.string.done_comment)
    Text(
        modifier = Modifier.padding(0.dp, 0.dp, 35.dp, 0.dp),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
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
}