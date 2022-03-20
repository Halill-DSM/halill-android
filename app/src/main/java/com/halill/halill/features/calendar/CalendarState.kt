package com.halill.halill.features.calendar

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState
import java.time.LocalDate

data class CalendarState(
    val currentDateTime: LocalDate,
    val selectedDateTime: LocalDate,
    val showingMonthDate: LocalDate,
    val selectedDateTodoList: List<TodoEntity>
): MviState {
    companion object {
        fun initial(): CalendarState =
            CalendarState(
                currentDateTime = LocalDate.now(),
                selectedDateTime = LocalDate.now(),
                showingMonthDate = LocalDate.now(),
                selectedDateTodoList = emptyList()
            )
    }
}