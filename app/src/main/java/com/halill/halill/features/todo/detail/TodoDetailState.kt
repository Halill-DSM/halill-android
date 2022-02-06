package com.halill.halill.features.todo.detail

import com.halill.domain.features.todo.entity.TodoEntity

sealed class TodoDetailState {
    object LoadingState : TodoDetailState()
    data class MainState(val todo: TodoEntity) : TodoDetailState()
}