package com.halill.halill.features.todo.write

import android.content.Context
import android.widget.NumberPicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill.R
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import com.halill.halill.util.lastDateOfMonth
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun WriteTodo(
    navController: NavController,
    todoId: Long,
    viewModel: WriteTodoViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    if (todoId.isEdit()) {
        LaunchedEffect(key1 = Unit) {
            viewModel.getTodoDataWhenEdit(todoId)
        }
    }
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    val title = if (todoId.isEdit()) {
                        stringResource(id = R.string.edit_todo)
                    } else {
                        stringResource(id = R.string.write_todo)
                    }
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                backgroundColor = Teal700,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleTextField(
                    state,
                    doOnTitleChange = { title ->
                        viewModel.setTitle(title)
                    }
                )
                ContentTextField(
                    state,
                    doOnContentChange = { content ->
                        viewModel.setContent(content)
                    }
                )
                DeadLineView(
                    state,
                    doOnDeadlineDateClick = {
                        viewModel.showSelectDateState()
                    },
                    doOnDeadlineTimeClick = {
                        viewModel.showSelectTimeState()
                    }
                )
                WriteTodoButton(
                    navController,
                    state,
                    doOnWriteTodoButtonClick = {
                        viewModel.writeTodo()
                    },
                    doOnEditTodoButtonClick = {
                        viewModel.editTodo()
                    }
                )

                if (state.showDateSelectDialog) {
                    SelectDateDialog(
                        state = state,
                        doOnSelectDateDialogDismiss = {
                            viewModel.dismissSelectDateState()
                        },
                        doOnDatePick = { day ->
                            viewModel.setDeadlineDay(day)
                        },
                        doOnMonthPick = { month ->
                            viewModel.setDeadlineMonth(month)
                        },
                        doOnYearPick = { year ->
                            viewModel.setDeadlineYear(year)
                        }
                    )
                    ClearFocus()
                } else if (state.showHourSelectDialog) {
                    SelectTimeDialog(
                        state = state,
                        doOnHourPick = { hour ->
                            viewModel.setDeadlineHour(hour)
                        },
                        doOnMinutePick = { minute ->
                            viewModel.setDeadlineMinute(minute)
                        },
                        doOnTimeDialogDismiss = {
                            viewModel.dismissSelectTimeState()
                        }
                    )
                    ClearFocus()
                }
            }
        })

}

private fun Long.isEdit(): Boolean =
    this != -1L

@Composable
fun TitleTextField(state: WriteTodoState, doOnTitleChange: (String) -> Unit) {
    val title = state.title
    val titleLabel = stringResource(id = R.string.write_title)
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = title,
        label = { Text(text = titleLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        onValueChange = {
            doOnTitleChange(it)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )
}

@Composable
fun ContentTextField(state: WriteTodoState, doOnContentChange: (String) -> Unit) {
    val content = state.content
    val contentLabel = stringResource(id = R.string.write_content)
    OutlinedTextField(
        value = content,
        label = { Text(text = contentLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp, minWidth = 100.dp)
            .padding(10.dp),
        onValueChange = {
            doOnContentChange(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Default
        )
    )
}

@Composable
fun DeadLineView(
    state: WriteTodoState,
    doOnDeadlineDateClick: () -> Unit,
    doOnDeadlineTimeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.End
    ) {
        val deadlineText = stringResource(id = R.string.dead_line)
        Text(modifier = Modifier.weight(2f), text = deadlineText)
        Column(horizontalAlignment = Alignment.End) {
            DeadlineDateView(
                state = state,
                doOnDeadlineDateClick = doOnDeadlineDateClick
            )
            DeadlineTimeView(
                state,
                doOnDeadlineTimeClick = doOnDeadlineTimeClick
            )
        }
    }
}

@Composable
fun DeadlineDateView(
    state: WriteTodoState, doOnDeadlineDateClick: () -> Unit
) {
    val deadLine = state.deadline
    val year = "${deadLine.year}년"
    val month = "${deadLine.monthValue}월"
    val date = "${deadLine.dayOfMonth}일"
    val dateText = "$year $month $date"
    Text(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .clickable(enabled = true, role = Role.Button) {
                doOnDeadlineDateClick()
            },
        text = dateText,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )

}

@Composable
fun DeadlineTimeView(
    state: WriteTodoState,
    doOnDeadlineTimeClick: () -> Unit
) {
    val deadline = state.deadline
    val hour = "${deadline.hour}시"
    val minute = "${deadline.minute}분"
    val timeText = "$hour $minute"
    Text(
        modifier = Modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .clickable(enabled = true, role = Role.Button) {
                doOnDeadlineTimeClick()
            },
        text = timeText,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )
}

@Composable
fun ClearFocus() {
    val focus = LocalFocusManager.current
    focus.clearFocus()
}

@Composable
fun WriteTodoButton(
    navController: NavController,
    state: WriteTodoState,
    doOnWriteTodoButtonClick: () -> Unit,
    doOnEditTodoButtonClick: () -> Unit
) {
    val emptyComment = stringResource(id = R.string.login_empty_comment)
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            if (state.doneInput()) {
                if (state.isEdit) {
                    doOnEditTodoButtonClick()
                } else {
                    doOnWriteTodoButtonClick()
                }
                navController.popBackStack()
            } else {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        emptyComment,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (state.doneInput()) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        val text =
            if (state.isEdit) stringResource(id = R.string.edit_todo) else stringResource(id = R.string.write_todo)
        Text(text = text)
    }
}

private fun WriteTodoState.doneInput() =
    title.isNotEmpty() && content.isNotEmpty()

@Composable
fun SelectDateDialog(
    state: WriteTodoState,
    doOnSelectDateDialogDismiss: () -> Unit,
    doOnDatePick: (Int) -> Unit,
    doOnMonthPick: (Int) -> Unit,
    doOnYearPick: (Int) -> Unit
) {
    Dialog({ doOnSelectDateDialogDismiss() }) {
        Surface(
            modifier = Modifier
                .width(275.dp),
            color = Color.White,
            shape = RoundedCornerShape(15.dp)
        ) {
            DateDialogContent(
                state = state,
                doOnDatePick = doOnDatePick,
                doOnMonthPick = doOnMonthPick,
                doOnYearPick = doOnYearPick
            )
        }
    }
}

@Composable
fun DateDialogContent(
    state: WriteTodoState,
    doOnDatePick: (Int) -> Unit,
    doOnMonthPick: (Int) -> Unit,
    doOnYearPick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        YearNumberPicker(
            state = state,
            doOnYearPick = doOnYearPick
        )
        val yearText = stringResource(id = R.string.year)
        Text(text = yearText)

        MonthNumberPicker(
            state = state,
            doOnMonthPick = doOnMonthPick
        )
        val monthText = stringResource(id = R.string.month)
        Text(text = monthText)

        DateNumberPicker(
            state = state,
            doOnDatePick = doOnDatePick
        )
        val dayText = stringResource(id = R.string.day)
        Text(text = dayText)
    }
}

@Composable
fun YearNumberPicker(doOnYearPick: (Int) -> Unit, state: WriteTodoState) {
    val deadline = state.deadline
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            yearNumberPicker(doOnYearPick, context, deadline.year)
        }
    )
}

private fun yearNumberPicker(doOnYearPick: (Int) -> Unit, context: Context, year: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            doOnYearPick(picker.value)
        }
        maxValue = year + 1
        minValue = year - 1
        value = year
    }

@Composable
fun MonthNumberPicker(state: WriteTodoState, doOnMonthPick: (Int) -> Unit) {
    val deadline = state.deadline
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            monthNumberPicker(doOnMonthPick, context, deadline.monthValue)
        }
    )
}

private fun monthNumberPicker(doOnMonthPick: (Int) -> Unit, context: Context, month: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            doOnMonthPick(picker.value)
        }
        maxValue = 12
        minValue = 1
        value = month
    }

@Composable
fun DateNumberPicker(state: WriteTodoState, doOnDatePick: (Int) -> Unit) {
    val deadline = state.deadline
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            dateNumberPicker(doOnDatePick, context, deadline)
        }
    )
}

private fun dateNumberPicker(doOnDatePick: (Int) -> Unit, context: Context, deadline: LocalDateTime) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            doOnDatePick(picker.value)
        }
        maxValue = deadline.lastDateOfMonth()
        minValue = 1
        value = deadline.dayOfMonth
    }

@Composable
fun SelectTimeDialog(
    state: WriteTodoState,
    doOnHourPick: (Int) -> Unit,
    doOnMinutePick: (Int) -> Unit,
    doOnTimeDialogDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { doOnTimeDialogDismiss() }) {
        Surface(
            modifier = Modifier
                .width(220.dp),
            color = Color.White,
            shape = RoundedCornerShape(15.dp)
        ) {
            TimeDialogContent(state, doOnMinutePick = doOnMinutePick, doOnHourPick = doOnHourPick)
        }
    }
}

@Composable
fun TimeDialogContent(
    state: WriteTodoState,
    doOnMinutePick: (Int) -> Unit,
    doOnHourPick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HourNumberPicker(state, doOnHourPick)
        val yearText = stringResource(id = R.string.hour)
        Text(text = yearText)

        MinuteNumberPicker(state, doOnMinutePick)
        val monthText = stringResource(id = R.string.minute)
        Text(text = monthText)
    }
}

@Composable
fun HourNumberPicker(state: WriteTodoState, doOnHourPick: (Int) -> Unit) {
    val deadline = state.deadline
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            hourNumberPicker(doOnHourPick, context, deadline.hour)
        }
    )
}

private fun hourNumberPicker(doOnHourPick: (Int) -> Unit, context: Context, hour: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            doOnHourPick(picker.value)
        }
        maxValue = 23
        minValue = 0
        value = hour
    }

@Composable
fun MinuteNumberPicker(state: WriteTodoState, doOnMinutePick: (Int) -> Unit) {
    val deadline = state.deadline
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            minuteNumberPicker(doOnMinutePick, context, deadline.minute)
        }
    )
}

private fun minuteNumberPicker(doOnMinutePick: (Int) -> Unit, context: Context, minute: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            doOnMinutePick(picker.value)
        }
        maxValue = 23
        minValue = 0
        value = minute
    }