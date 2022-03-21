package com.halill.halill.features.calendar

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState
import com.halill.halill.util.toMontDayList
import java.time.LocalDate

data class CalendarState(
    val currentDate: LocalDate,
    val selectedDate: LocalDate,
    val showingMonthDate: LocalDate,
    val showingMonthDayList: List<LocalDate>,
    val selectedDateTodoList: List<TodoEntity>,
    val dateTodoMap: Map<LocalDate, TodoEntity>
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