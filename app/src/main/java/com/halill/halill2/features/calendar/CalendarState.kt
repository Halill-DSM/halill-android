package com.halill.halill2.features.calendar

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill2.base.MviState
import com.halill.halill2.util.toMontDayList
import java.time.LocalDate

data class CalendarState(
    val currentDate: LocalDate,
    val selectedDate: LocalDate,
    val showingMonthDate: LocalDate,
    val showingMonthDayList: List<LocalDate>,
    val selectedDateTodoList: List<TodoEntity>,
    val dateTodoMap: Map<LocalDate, List<TodoEntity>>
) : MviState {

    companion object {
        fun initial(): CalendarState =
            CalendarState(
                currentDate = LocalDate.now(),
                selectedDate = LocalDate.now(),
                showingMonthDate = LocalDate.now(),
                showingMonthDayList = LocalDate.now().toMontDayList(),
                selectedDateTodoList = emptyList(),
                dateTodoMap = emptyMap()
            )
    }
}