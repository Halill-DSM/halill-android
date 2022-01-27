package com.halill.halill.main.model

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity

sealed class MainState {
    object LoadingState : MainState()
    data class ShowTodoListState(val userEntity: UserEntity, val todoList: List<TodoEntity>, val doneList: List<TodoEntity>) : MainState()
    data class EmptyListState(val userEntity: UserEntity) : MainState()
}
