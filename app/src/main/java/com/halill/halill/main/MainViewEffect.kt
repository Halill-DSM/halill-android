package com.halill.halill.main

sealed class MainViewEffect {
    object StartLogin : MainViewEffect()
    object DoneDeleteTodo : MainViewEffect()
    data class StartTodoDetail(val id: Long) : MainViewEffect()
}