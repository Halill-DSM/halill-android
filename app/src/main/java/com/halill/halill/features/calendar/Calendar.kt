package com.halill.halill.features.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun Calendar(viewModel: CalendarViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    CalendarContent(
        state = state,
        doOnNextClick = {
            viewModel.showNextMonth()
        },
        doOnBeforeMonthClick = {
            viewModel.showBeforeMonth()
        }
    )
}

@Composable
fun CalendarContent(
    state: CalendarState,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextClick: () -> Unit
) {
    Column {
        CalendarMonthLayout(
            state.showingMonthDate,
            doOnBeforeMonthClick = doOnBeforeMonthClick,
            doOnNextClick = doOnNextClick
        )
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
        IconButton(onClick = doOnBeforeMonthClick, modifier = Modifier
            .padding(10.dp)
            .size(60.dp)) {
            Icon(Icons.Filled.NavigateBefore, contentDescription = "beforeMonth")
        }

        val monthText = "${showingDate.year}년 ${showingDate.monthValue}월"
        Text(text = monthText, fontSize = 22.sp)

        IconButton(onClick = doOnNextClick, modifier = Modifier
            .padding(10.dp)
            .size(60.dp)) {
            Icon(Icons.Filled.NavigateNext, "nextMonth")

        }
    }
}