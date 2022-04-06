package com.halill.halill2.features.calendar

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.halill2.features.list.TodoList
import com.halill.halill2.util.isToday
import com.halill.halill2.util.toMontDayList
import java.time.LocalDate
import kotlin.math.abs

@Composable
fun Calendar(navController: NavController, viewModel: CalendarViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state) {
        viewModel.fetchDateTodoMap()
    }

    LaunchedEffect(state.selectedDate) {
        viewModel.fetchTodoListWithDate()
    }

    CalendarContent(
        state = state,
        doOnNextMonthClick = {
            viewModel.showNextMonth()
        },
        doOnBeforeMonthClick = {
            viewModel.showBeforeMonth()
        },
        doOnDateSelect = { date ->
            viewModel.selectDate(date)
        },
        doOnTodoClick = { id ->
            navController.navigate("todoDetail/$id")
        },
        doOnDoneClick = { id ->
            viewModel.doneTodo(id)
        },
        doOnDeleteClick = { id ->
            viewModel.deleteTodo(id)
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CalendarContent(
    state: CalendarState,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextMonthClick: () -> Unit,
    doOnDateSelect: (LocalDate) -> Unit,
    doOnTodoClick: (Long) -> Unit,
    doOnDoneClick: (Long) -> Unit,
    doOnDeleteClick: (Long) -> Unit
) {
    Column {
        CalendarMonthLayout(
            state.showingMonthDate,
            doOnBeforeMonthClick = doOnBeforeMonthClick,
            doOnNextMonthClick = doOnNextMonthClick
        )
        WeekTextLinearLayout()
        Divider(color = MaterialTheme.colors.onSurface)

        AnimatedContent(
            targetState = state.showingMonthDate,
            transitionSpec = {
                if (targetState.isBefore(initialState)) {
                    slideInHorizontally { horizontal -> -horizontal } + fadeIn() with
                            slideOutHorizontally { horizontal -> horizontal } + fadeOut()
                } else {
                    slideInHorizontally { horizontal -> horizontal } + fadeIn() with
                            slideOutHorizontally { horizontal -> -horizontal } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { showingDate ->
            CalendarView(
                showingDate = showingDate,
                doOnDateSelect = doOnDateSelect,
                showBeforeMonth = doOnBeforeMonthClick,
                showNextMonth = doOnNextMonthClick
            )
        }

        Divider(color = MaterialTheme.colors.onSurface)
        TodoList(
            todoList = state.selectedDateTodoList,
            onItemClick = doOnTodoClick,
            onDoneClick = doOnDoneClick,
            onDeleteClick = doOnDeleteClick
        )
    }
}

@Composable
fun WeekTextLinearLayout() {
    WeekLineLayout {
        val weekTextList = remember {
            listOf("일", "월", "화", "수", "목", "금", "토")
        }
        weekTextList.forEach { weekText ->
            val textColor =
                if (weekText == "일") Color.Red else if (weekText == "토") Color.Blue else Color.Unspecified
            WeekText(weekText = weekText, textColor = textColor)
        }
    }
}

@Composable
fun WeekText(weekText: String, textColor: Color) {
    Text(
        text = weekText,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.size(calendarItemWidth)
    )
}

@Composable
fun CalendarMonthLayout(
    showingDate: LocalDate,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextMonthClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = doOnBeforeMonthClick, modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
        ) {
            Icon(
                Icons.Filled.NavigateBefore,
                contentDescription = "beforeMonth",
                modifier = Modifier.fillMaxWidth()
            )
        }

        val monthText = "${showingDate.year}년 ${showingDate.monthValue}월"
        Text(text = monthText, fontSize = 22.sp)

        IconButton(
            onClick = doOnNextMonthClick, modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
        ) {
            Icon(
                Icons.Filled.NavigateNext,
                contentDescription = "nextMonth",
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Composable
fun CalendarView(
    doOnDateSelect: (LocalDate) -> Unit,
    showNextMonth: () -> Unit,
    showBeforeMonth: () -> Unit,
    showingDate: LocalDate
) {
    var changedDelta by remember {
        mutableStateOf(0f)
    }
    Column(
        modifier = Modifier
            .padding(0.dp, 7.dp, 0.dp, 0.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    if (abs(delta) < 250) {
                        changedDelta += delta
                    }
                    when {
                        changedDelta > 300 -> {
                            showNextMonth()
                            changedDelta = 0f
                        }
                        changedDelta < -300 -> {
                            showBeforeMonth()
                            changedDelta = 0f
                        }
                    }
                },
                reverseDirection = true
            )
    ) {
        val monthDayList = showingDate.toMontDayList()
        val firstDayOfWeek = monthDayList[0].dayOfWeek.value
        val alreadyShowDateCount = if (firstDayOfWeek == 7) 7 else (7 - firstDayOfWeek)
        CalendarFirstWeekLayout(
            monthDayList = monthDayList,
            doOnDateSelect = doOnDateSelect,
            showingMonthDayList = monthDayList
        )
        CalendarWeekLayout(
            showingMonthDayList = monthDayList,
            alreadyShowDateCount = alreadyShowDateCount,
            doOnDateSelect = doOnDateSelect
        )
    }
}

val calendarItemWidth = 36.dp
val calendarItemHeight = 55.dp

@Composable
fun CalendarFirstWeekLayout(
    monthDayList: List<LocalDate>,
    showingMonthDayList: List<LocalDate>,
    doOnDateSelect: (LocalDate) -> Unit
) {
    val firstDayOfWeek = monthDayList[0].dayOfWeek.value
    WeekLineLayout {
        CalendarFrontSpacer(dayOfWeek = firstDayOfWeek)

        FirstWeekLayout(
            doOnDateSelect = doOnDateSelect,
            showingMonthDayList = showingMonthDayList
        )
    }
}

@Composable
fun CalendarFrontSpacer(dayOfWeek: Int) {
    if (dayOfWeek == 7) {
        return
    }
    for (ct in 0 until dayOfWeek) {
        Spacer(
            modifier = Modifier
                .size(calendarItemWidth, calendarItemHeight)
        )
    }
}

@Composable
fun FirstWeekLayout(
    doOnDateSelect: (LocalDate) -> Unit,
    showingMonthDayList: List<LocalDate>
) {
    for (day in showingMonthDayList) {
        CalendarDayItem(day, doOnDateSelect = doOnDateSelect)
        if (day.dayOfWeek.value == 6) {
            break
        }
    }
}

@Composable
fun CalendarWeekLayout(
    showingMonthDayList: List<LocalDate>,
    alreadyShowDateCount: Int,
    doOnDateSelect: (LocalDate) -> Unit
) {
    val dayList = showingMonthDayList.toMutableList().apply {
        for (ct in 0 until alreadyShowDateCount) {
            removeAt(0)
        }
    }

    var showedDateCount = alreadyShowDateCount

    Column {
        while (dayList.isNotEmpty()) {
            WeekLineLayout {
                for (ct in 0 until 7) {
                    if (showedDateCount >= showingMonthDayList.size) {
                        Spacer(
                            modifier = Modifier.size(
                                calendarItemWidth,
                                calendarItemHeight
                            )
                        )
                    } else {
                        CalendarDayItem(
                            showingMonthDayList[showedDateCount],
                            doOnDateSelect = doOnDateSelect
                        )
                        dayList.removeAt(0)
                        showedDateCount += 1
                    }
                }
            }
        }
    }
}

@Composable
fun WeekLineLayout(content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp)
    ) {
        content()
    }
}

@Composable
fun CalendarDayItem(
    day: LocalDate,
    doOnDateSelect: (LocalDate) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val borderColor =
        if (day.isToday()) MaterialTheme.colors.primary else MaterialTheme.colors.background
    val textColor = if (day.isToday()) Color.White else Color.Unspecified

    val selectedBackGroundColor =
        if (state.selectedDate == day) MaterialTheme.colors.primary else MaterialTheme.colors.background
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(calendarItemWidth, calendarItemHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                doOnDateSelect(day)
            }
            .border(1.dp, color = selectedBackGroundColor)
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            color = textColor,
            modifier = Modifier
                .background(borderColor)
                .size(25.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        val dayTodoList = state.dateTodoMap[day] ?: emptyList()
        if (dayTodoList.isNotEmpty()) {
            if (dayTodoList.size > 4) {
                dayTodoList.subList(0, 3)
            }
            dayTodoList.forEach { todo ->
                val color =
                    if (todo.isCompleted) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
                Divider(thickness = 3.dp, color = color, modifier = Modifier.padding(0.dp, 2.dp))
            }
        }
    }

}