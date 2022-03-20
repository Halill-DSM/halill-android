package com.halill.halill.features.calendar

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState
import java.time.LocalDate
import java.time.LocalDateTime

data class CalendarState(
    val currentDateTime: LocalDateTime,
    val selectedDateTime: LocalDateTime,
    val showingMonthDate: LocalDate,
    val selectedDateTodoList: List<TodoEntity>
): MviState {
    companion object {
        fun initial(): CalendarState =
            CalendarState(
                currentDateTime = LocalDateTime.now(),
                selectedDateTime = LocalDateTime.now(),
                showingMonthDate = LocalDate.now(),
                selectedDateTodoList = emptyList()
            )
    }
}