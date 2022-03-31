package com.halill.halill2.features.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import java.time.LocalDate

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
        doOnNextClick = {
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

@Composable
fun CalendarContent(
    state: CalendarState,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextClick: () -> Unit,
    doOnDateSelect: (LocalDate) -> Unit,
    doOnTodoClick: (Long) -> Unit,
    doOnDoneClick: (Long) -> Unit,
    doOnDeleteClick: (Long) -> Unit
) {
    Column {
        CalendarMonthLayout(
            state.showingMonthDate,
            doOnBeforeMonthClick = doOnBeforeMonthClick,
            doOnNextClick = doOnNextClick
        )
        WeekTextLinearLayout()
        Divider(color = MaterialTheme.colors.onSurface)
        CalendarView(state, doOnDateSelect)
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
    doOnNextClick: () -> Unit
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
            onClick = doOnNextClick, modifier = Modifier
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
fun CalendarView(state: CalendarState, doOnDateSelect: (LocalDate) -> Unit) {
    Column(modifier = Modifier.padding(0.dp, 7.dp, 0.dp, 0.dp)) {
        val monthDayList = state.showingMonthDayList
        val firstDayOfWeek = monthDayList[0].dayOfWeek.value
        val alreadyShowDateCount = if (firstDayOfWeek == 7) 7 else (7 - firstDayOfWeek)
        CalendarFirstWeekLayout(
            state = state,
            firstDayOfWeek = firstDayOfWeek,
            doOnDateSelect = doOnDateSelect
        )
        CalendarWeekLayout(
            state = state,
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
    state: CalendarState,
    firstDayOfWeek: Int,
    doOnDateSelect: (LocalDate) -> Unit
) {
    WeekLineLayout {
        CalendarFrontSpacer(dayOfWeek = firstDayOfWeek)

        FirstWeekLayout(
            state = state,
            showingMonthDayList = state.showingMonthDayList,
            doOnDateSelect = doOnDateSelect
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
    state: CalendarState,
    showingMonthDayList: List<LocalDate>,
    doOnDateSelect: (LocalDate) -> Unit
) {
    for (day in showingMonthDayList) {
        CalendarDayItem(day, doOnDateSelect = doOnDateSelect, state = state)
        if (day.dayOfWeek.value == 6) {
            break
        }
    }
}

@Composable
fun CalendarWeekLayout(
    state: CalendarState,
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
                            doOnDateSelect = doOnDateSelect,
                            state = state
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
fun CalendarDayItem(day: LocalDate, state: CalendarState, doOnDateSelect: (LocalDate) -> Unit) {
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