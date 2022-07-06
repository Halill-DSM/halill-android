package com.halill.halill2.features.write

import com.halill.halill2.base.MviEvent
import java.time.LocalDateTime

sealed class WriteTodoEvent : MviEvent {
    data class InputTitle(val title: String) : WriteTodoEvent()
    data class InputContent(val content: String) : WriteTodoEvent()
    data class InputDeadlineYear(val year: Int) : WriteTodoEvent()
    data class InputDeadlineMonth(val month: Int) : WriteTodoEvent()
    data class InputDeadlineDay(val day: Int) : WriteTodoEvent()
    data class InputDeadlineHour(val hour: Int) : WriteTodoEvent()
    data class InputDeadlineMinute(val minute: Int) : WriteTodoEvent()
    data class InputDeadline(val deadline: LocalDateTime) : WriteTodoEvent()
    data class IsForEdit(val editTodoId: Long, val isDone: Boolean) : WriteTodoEvent()
    object ShowSelectDateDialog : WriteTodoEvent()
    object ShowSelectTimeDialog : WriteTodoEvent()
    object DismissSelectDateDialog : WriteTodoEvent()
    object DismissSelectTimeDialog : WriteTodoEvent()
}