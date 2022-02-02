package com.halill.halill.features.todo

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
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
import com.halill.halill.features.auth.login.LoginLayoutViews
import com.halill.halill.features.todo.model.WriteTodoState
import com.halill.halill.main.scaffoldState
import com.halill.halill.ui.theme.Teal700
import com.halill.halill.ui.theme.Teal900
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun WriteTodo(navController: NavController, viewModel: WriteTodoViewModel = hiltViewModel()) {
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.write_todo))
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
                TitleTextField()
                ContentTextField()
                DeadLineView()
                WriteTodoButton()
                val writeTodoState = viewModel.writeTodoState.collectAsState().value
                if (writeTodoState is WriteTodoState.SelectDateState) {
                    SelectDateDialog()
                    ClearFocus()
                } else if (writeTodoState is WriteTodoState.SelectTimeState) {
                    SelectTimeDialog()
                    ClearFocus()
                }
            }
        })

}

@Composable
fun TitleTextField(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val title = viewModel.title.collectAsState()
    val titleLabel = stringResource(id = R.string.write_title)
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = title.value,
        label = { Text(text = titleLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        onValueChange = {
            viewModel.setTitle(it)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )
}

@Composable
fun ContentTextField(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val content = viewModel.content.collectAsState()
    val contentLabel = stringResource(id = R.string.write_content)
    OutlinedTextField(
        value = content.value,
        label = { Text(text = contentLabel) },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp, minWidth = 100.dp)
            .padding(10.dp),
        onValueChange = {
            viewModel.setContent(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Default
        )
    )
}

@Composable
fun DeadLineView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.End
    ) {
        val deadLineText = stringResource(id = R.string.dead_line)
        Text(modifier = Modifier.weight(2f), text = deadLineText)
        Column(horizontalAlignment = Alignment.End) {
            DeadLineDateView()
            DeadLineTimeView()
        }
    }
}

@Composable
fun DeadLineDateView(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadLine = viewModel.deadLine.value
    val year = "${deadLine.year}년"
    val month = "${deadLine.monthValue}월"
    val date = "${deadLine.dayOfMonth}일"
    val dateText = "$year $month $date"
    Text(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .clickable(enabled = true, role = Role.Button) {
                viewModel.setSelectDateState()
            },
        text = dateText,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )

}

@Composable
fun DeadLineTimeView(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadLine = viewModel.deadLine.value
    val hour = "${deadLine.hour}시"
    val minute = "${deadLine.minute}분"
    val timeText = "$hour $minute"
    Text(
        modifier = Modifier
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .clickable(enabled = true, role = Role.Button) {
                viewModel.setSelectTimeState()
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
fun WriteTodoButton(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val writeTodoState = viewModel.writeTodoState.collectAsState()
    val emptyComment = stringResource(id = R.string.login_empty_comment)
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            if (writeTodoState.value is WriteTodoState.DoneInputState) {
                viewModel.writeTodo()
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
            backgroundColor = if (writeTodoState.value is WriteTodoState.DoneInputState) Teal900 else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .layoutId(LoginLayoutViews.LoginButton)
            .width(200.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Text(text = stringResource(id = R.string.write_todo))
    }
}

@Composable
fun SelectDateDialog(viewModel: WriteTodoViewModel = hiltViewModel()) {
    Dialog(onDismissRequest = { viewModel.checkDoneInput() }) {
        Surface(
            modifier = Modifier
                .width(275.dp),
            color = Color.White,
            shape = RoundedCornerShape(15.dp)
        ) {
            DateDialogContent()
        }
    }
}

@Composable
fun DateDialogContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        YearNumberPicker()
        val yearText = stringResource(id = R.string.year)
        Text(text = yearText)

        MonthNumberPicker()
        val monthText = stringResource(id = R.string.month)
        Text(text = monthText)

        DateNumberPicker()
        val dayText = stringResource(id = R.string.day)
        Text(text = dayText)
    }
}

@Composable
fun YearNumberPicker(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadline = viewModel.deadLine.collectAsState().value
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            yearNumberPicker(context, deadline.year)
        }
    )
}

private fun yearNumberPicker(context: Context, year: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            picker.value
        }
        maxValue = year + 1
        minValue = year - 1
        value = year
    }

@Composable
fun MonthNumberPicker(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadline = viewModel.deadLine.collectAsState().value
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            monthNumberPicker(context, deadline.monthValue)
        }
    )
}

private fun monthNumberPicker(context: Context, month: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            picker.value
        }
        maxValue = 12
        minValue = 1
        value = month
    }

@Composable
fun DateNumberPicker(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadline = viewModel.deadLine.collectAsState().value
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            dateNumberPicker(context, deadline.dayOfMonth, deadline.endDayOfMonth())
        }
    )
}

private fun LocalDateTime.endDayOfMonth(): Int {
    val localDate = LocalDate.of(year, month, dayOfMonth)
    return localDate.withDayOfMonth(localDate.lengthOfMonth()).dayOfMonth
}

private fun dateNumberPicker(context: Context, day: Int, lastDay: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            picker.value
        }
        maxValue = lastDay
        minValue = 1
        value = day
    }

@Composable
fun SelectTimeDialog(viewModel: WriteTodoViewModel = hiltViewModel()) {
    Dialog(onDismissRequest = { viewModel.checkDoneInput() }) {
        Surface(
            modifier = Modifier
                .width(220.dp),
            color = Color.White,
            shape = RoundedCornerShape(15.dp)
        ) {
            TimeDialogContent()
        }
    }
}

@Composable
fun TimeDialogContent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HourNumberPicker()
        val yearText = stringResource(id = R.string.hour)
        Text(text = yearText)

        MinuteNumberPicker()
        val monthText = stringResource(id = R.string.minute)
        Text(text = monthText)
    }
}

@Composable
fun HourNumberPicker(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadline = viewModel.deadLine.collectAsState().value
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            hourNumberPicker(context, deadline.hour)
        }
    )
}

private fun hourNumberPicker(context: Context, hour: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            picker.value
        }
        maxValue = 23
        minValue = 0
        value = hour
    }

@Composable
fun MinuteNumberPicker(viewModel: WriteTodoViewModel = hiltViewModel()) {
    val deadline = viewModel.deadLine.collectAsState().value
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            minuteNumberPicker(context, deadline.minute)
        }
    )
}

private fun minuteNumberPicker(context: Context, minute: Int) =
    NumberPicker(context).apply {
        setOnValueChangedListener { picker, _, _ ->
            picker.value
        }
        maxValue = 23
        minValue = 0
        value = minute
    }

private fun setDeadlineDate() {

}