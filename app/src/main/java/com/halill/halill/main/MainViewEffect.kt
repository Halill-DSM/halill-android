package com.halill.halill.main

sealed class MainViewEffect {
    object DoneDeleteTodo : MainViewEffect()
    data class StartTodoDetail(val id: Long) : MainViewEffect()
}