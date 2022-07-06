package com.halill.halill2.features.calendar

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.FetchDateTodoMapUseCase
import com.halill.domain.features.todo.usecase.FetchTodoListWithDateUseCase
import com.halill.halill2.base.BaseViewModel
import com.halill.halill2.util.toMontDayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val doneTodoUseCase: DoneTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val fetchTodoListWithDateUseCase: FetchTodoListWithDateUseCase,
    private val fetchDateTodoMapUseCase: FetchDateTodoMapUseCase
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

    suspend fun fetchDateTodoMap() {
        val todoMap = fetchDateTodoMapUseCase.execute(Unit)
        sendEvent(CalendarEvent.SetDateTodoMap(todoMap))
    }

    fun doneTodo(id: Long) {
        viewModelScope.launch {
            doneTodoUseCase.execute(id)
            fetchTodoListWithDate()
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(id)
            fetchTodoListWithDate()
        }
    }

    suspend fun fetchTodoListWithDate() {
        val selectedDate = state.value.selectedDate
        val todoList = fetchTodoListWithDateUseCase.execute(selectedDate)
        sendEvent(CalendarEvent.ShowDateTodoList(todoList))
    }

    override fun reduceEvent(oldState: CalendarState, event: CalendarEvent) {
        when (event) {
            is CalendarEvent.NextMonth -> {
                setState(
                    oldState.copy(
                        showingMonthDate = oldState.showingMonthDate.plusMonths(1),
                        showingMonthDayList = oldState.showingMonthDate.plusMonths(1)
                            .toMontDayList()
                    )
                )
            }

            is CalendarEvent.BeforeMonth -> {
                setState(
                    oldState.copy(
                        showingMonthDate = oldState.showingMonthDate.minusMonths(1),
                        showingMonthDayList = oldState.showingMonthDate.minusMonths(1)
                            .toMontDayList()
                    )
                )
            }

            is CalendarEvent.SelectDate -> {
                setState(
                    oldState.copy(
                        selectedDate = event.date
                    )
                )
            }

            is CalendarEvent.ShowDateTodoList -> {
                setState(
                    oldState.copy(
                        selectedDateTodoList = event.todolist
                    )
                )
            }

            is CalendarEvent.SetDateTodoMap -> {
                setState(
                    oldState.copy(
                        dateTodoMap = event.dateTodoMap
                    )
                )
            }
        }
    }
}