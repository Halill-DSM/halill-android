package com.halill.halill.main.model

import com.halill.halill.base.MviEvent

sealed class MainEvent : MviEvent {
    object StartLogin : MainEvent()
    object DoneDeleteTodo : MainEvent()
    data class StartTodoDetail(val id: Long) : MainEvent()
}