package com.halill.halill.features.calendar

import com.halill.domain.features.todo.usecase.FetchTodoListWithDateUseCase
import com.halill.halill.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val fetchTodoListWithDateUseCase: FetchTodoListWithDateUseCase
) : BaseViewModel<CalendarState, CalendarEvent>() {

    override val initialState: CalendarState
        get() = CalendarState.initial()

    fun showNextMonth() {
        sendEvent(CalendarEvent.NextMonth)
    }

    fun showBeforeMonth() {
        sendEvent(CalendarEvent.BeforeMonth)
    }

    fun selectDate(date: LocalDate) {
        sendEvent(CalendarEvent.SelectDate(date))
    }

    suspend fun fetchTodoListWithDate() {
        val selectedDate = state.value.selectedDateTime
        val todoList = fetchTodoListWithDateUseCase.execute(selectedDate)
        sendEvent(CalendarEvent.ShowDateTodoList(todoList))
    }

    override fun reduceEvent(oldState: CalendarState, event: CalendarEvent) {
        when (event) {
            is CalendarEvent.NextMonth -> {
                setState(
                    oldState.copy(
                        showingMonthDate = oldState.showingMonthDate.plusMonths(1)
                    )
                )
            }
            is CalendarEvent.BeforeMonth -> {
                setState(
                    oldState.copy(
                        showingMonthDate = oldState.showingMonthDate.minusMonths(1)
                    )
                )
            }
            is CalendarEvent.SelectDate -> {

            }
            is CalendarEvent.ShowDateTodoList -> {

            }
        }
    }
}