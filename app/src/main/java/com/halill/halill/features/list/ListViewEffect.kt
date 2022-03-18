package com.halill.halill.features.list

sealed class ListViewEffect {
    object DoneDeleteTodo : ListViewEffect()
    data class StartTodoDetail(val id: Long) : ListViewEffect()
}