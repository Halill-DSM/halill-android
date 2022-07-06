package com.halill.halill2.features.list

sealed class ListViewEffect {
    object DoneDeleteTodo : ListViewEffect()
    data class StartTodoDetail(val id: Long) : ListViewEffect()
}