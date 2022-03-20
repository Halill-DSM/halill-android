package com.halill.halill.features.calendar

import com.halill.halill.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(

) : BaseViewModel<CalendarState, CalendarEvent>() {

    override val initialState: CalendarState
        get() = CalendarState.initial()

    override fun reduceEvent(oldState: CalendarState, event: CalendarEvent) {
        when (event) {
            is CalendarEvent.NextMonth -> {

            }
            is CalendarEvent.BeforeMonth -> {

            }
            is CalendarEvent.SelectDate -> {

            }
            is CalendarEvent.ShowDateTodoList -> {

            }
        }
    }
}