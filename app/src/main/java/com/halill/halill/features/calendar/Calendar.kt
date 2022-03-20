package com.halill.halill.features.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun Calendar(viewModel: CalendarViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    CalendarContent(state = state)
}

@Composable
fun CalendarContent(
    state: CalendarState,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextClick: () -> Unit
) {
    Column {
        CalendarMonthLayout(
            state.showingMonthDate
        )
    }
}

@Composable
fun CalendarMonthLayout(
    showingDate: LocalDate,
    doOnBeforeMonthClick: () -> Unit,
    doOnNextClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = doOnBeforeMonthClick) {
            Icons.Filled.NavigateBefore
        }


        IconButton(onClick = doOnNextClick) {
            Icons.Filled.NavigateNext
        }
    }
}