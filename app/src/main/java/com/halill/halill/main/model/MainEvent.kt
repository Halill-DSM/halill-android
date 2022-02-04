package com.halill.halill.main.model

sealed class MainEvent {
    object StartLogin : MainEvent()
    object DoneDeleteTodo : MainEvent()
    data class StartTodoDetail(val id: Long) : MainEvent()
}