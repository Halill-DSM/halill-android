package com.halill.halill.features.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    }
}

@Composable
fun WeekTextLinearLayout() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp)
    ) {
        Text(text = "일", color = Color.Red)
        Text(text = "월", color = Color.Black)
        Text(text = "화", color = Color.Black)
        Text(text = "수", color = Color.Black)
        Text(text = "목", color = Color.Black)
        Text(text = "금", color = Color.Black)
        Text(text = "토", color = Color.Blue)
    }
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp)
        ) {

        }
    }
}