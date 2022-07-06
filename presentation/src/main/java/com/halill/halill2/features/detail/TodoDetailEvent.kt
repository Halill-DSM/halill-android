package com.halill.halill2.features.detail

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill2.base.MviEvent

sealed class TodoDetailEvent : MviEvent {
    data class ShowTodoDetail(val todo: TodoEntity) : TodoDetailEvent()
}