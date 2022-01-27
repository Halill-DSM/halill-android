package com.halill.halill.main.model

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity

sealed class MainState {
    object LoadingState : MainState()
    data class ShowTodoListState(val userEntity: UserEntity, val data: List<TodoEntity>) : MainState()
    data class ShowDoneListState(val userEntity: UserEntity, val data: List<TodoEntity>) : MainState()
    data class ErrorState(val date: String) : MainState()
    object EmptyListState : MainState()
}
