package com.halill.halill.main.model

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviEvent

sealed class MainEvent : MviEvent {
    object StartLogin : MainEvent()
    object DoneDeleteTodo : MainEvent()
    data class StartTodoDetail(val id: Long) : MainEvent()
    data class DoneLoadingTodo(
        val doneList: List<TodoEntity>,
        val todoList: List<TodoEntity>
    ) : MainEvent()
}