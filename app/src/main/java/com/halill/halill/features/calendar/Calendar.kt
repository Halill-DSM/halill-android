package com.halill.halill.features.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import com.halill.halill.ui.theme.Gray200
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun Calendar(viewModel: CalendarViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
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
            coroutineScope.launch {
                viewModel.fetchTodoListWithDate()
            }
        }
    )
}

@Composable
fun CalendarContent(
    state: CalendarState,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextClick: () -> Unit,
    doOnDateSelect: (LocalDate) -> Unit
) {
    Column {
        CalendarMonthLayout(
            state.showingMonthDate,
            doOnBeforeMonthClick = doOnBeforeMonthClick,
            doOnNextClick = doOnNextClick
        )
        WeekTextLinearLayout()
        Divider(color = Gray200, modifier = Modifier.padding(25.dp, 10.dp))
        CalendarView(state, doOnDateSelect)
        Divider(color = Gray200, modifier = Modifier.padding(25.dp, 10.dp))
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
                if (weekText == "일") Color.Red else if (weekText == "토") Color.Blue else Color.Black
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
    Column {
        val monthDayList = state.showingMonthDayList
        val firstDayOfWeek = monthDayList[0].dayOfWeek.value
        val alreadyShowDateCount = if (firstDayOfWeek == 7) 7 else (7 - firstDayOfWeek)
        CalendarFirstWeekLayout(
            state = state,
            firstDayOfWeek = firstDayOfWeek,
            doOnDateSelect = doOnDateSelect
        )
        CalendarWeekLayout(
            showingMonthDayList = monthDayList,
            alreadyShowDateCount = alreadyShowDateCount,
            doOnDateSelect = doOnDateSelect
        )

    }
}

val calendarItemWidth = 28.dp
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
    showingMonthDayList: List<LocalDate>,
    doOnDateSelect: (LocalDate) -> Unit
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
fun CalendarDayItem(day: LocalDate, doOnDateSelect: (LocalDate) -> Unit) {
    Text(
        text = day.dayOfMonth.toString(),
        textAlign = TextAlign.Center,
        modifier = Modifier.size(calendarItemWidth, calendarItemHeight)
    )
}