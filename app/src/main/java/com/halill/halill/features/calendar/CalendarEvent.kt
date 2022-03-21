package com.halill.halill.features.calendar

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviEvent
import java.time.LocalDate

sealed class CalendarEvent : MviEvent {
    data class SelectDate(val date: LocalDate) : CalendarEvent()
    data class ShowDateTodoList(val todolist: List<TodoEntity>) : CalendarEvent()
    data class SetDateTodoMap(val dateTodoMap: Map<LocalDate, List<TodoEntity>>) : CalendarEvent()
    object NextMonth : CalendarEvent()
    object BeforeMonth : CalendarEvent()
}