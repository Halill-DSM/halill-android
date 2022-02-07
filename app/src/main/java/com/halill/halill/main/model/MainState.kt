package com.halill.halill.main.model

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity

sealed class MainState(
    val user: UserEntity?,
    val isLoading: Boolean,
    val todoList: List<TodoEntity>,
    val doneList: List<TodoEntity>
) {
    object LoadingState : MainState(
        user = null,
        isLoading = true,
        todoList = emptyList(),
        doneList = emptyList()
    )

    data class ShowTodoListState(
        val userEntity: UserEntity,
        val todo: List<TodoEntity>,
        val done: List<TodoEntity>
    ) : MainState(
        user = userEntity,
        isLoading = false,
        todoList = todo,
        doneList = done
    )

    data class EmptyListState(val userEntity: UserEntity) : MainState(
        user = userEntity,
        isLoading = false,
        todoList = emptyList(),
        doneList = emptyList()
    )
}
