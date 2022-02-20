package com.halill.halill.features.todo.detail

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviEvent

sealed class TodoDetailEvent : MviEvent {
    data class ShowTodoDetail(val todo: TodoEntity) : TodoDetailEvent()
}